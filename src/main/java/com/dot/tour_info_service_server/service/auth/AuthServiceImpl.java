package com.dot.tour_info_service_server.service.auth;

import com.dot.tour_info_service_server.dto.*;
import com.dot.tour_info_service_server.dto.request.auth.ChangePasswordRequestDTO;
import com.dot.tour_info_service_server.dto.request.auth.EmailRequestDTO;
import com.dot.tour_info_service_server.dto.request.auth.LoginRequestDTO;
import com.dot.tour_info_service_server.dto.request.auth.SignupRequestDTO;
import com.dot.tour_info_service_server.dto.response.auth.LoginServiceDTO;
import com.dot.tour_info_service_server.entity.Disciplinary;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.mapper.MemberMapper;
import com.dot.tour_info_service_server.repository.DisciplinaryRepository;
import com.dot.tour_info_service_server.repository.MemberRepository;
import com.dot.tour_info_service_server.security.util.SecurityUtil;
import com.dot.tour_info_service_server.service.mail.MailService;
import com.dot.tour_info_service_server.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.security.auth.login.AccountNotFoundException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;
    private final DisciplinaryRepository disciplinaryRepository;

    private final MemberMapper memberMapper;

    //로그인
    @Override
    public LoginServiceDTO login(LoginRequestDTO requestDTO) {
        Member result=memberMapper.getLogin(requestDTO.getEmail());

        if (result==null){
            throw new BadCredentialsException("유저 정보가 없습니다");
        }
        Disciplinary disciplinary=disciplinaryRepository.reportList(result.getMno());


        if(!passwordEncoder.matches(requestDTO.getPassword(),result.getPassword())){
            throw new BadCredentialsException("Password가 일치하지 않습니다");
        }

        if(!result.isValidate()){
            throw new DisabledException("이메일 인증이 필요합니다.");
        }

        if (!result.isApprove()) {
            throw new DisabledException("관리자의 승인이 필요합니다.");
        }
        if( result.getDisciplinary()>=5 || (disciplinary!=null && disciplinary.getExpDate().isAfter(LocalDateTime.now()))){
            String expDate= disciplinary.getExpDate()==null? "무기한 ": disciplinary.getExpDate().toString();
            throw new DisabledException("정지된 회원입니다. 정지기간 : "+expDate);
        }
        LoginServiceDTO loginServiceDTO = LoginServiceDTO.builder()
                .mno(result.getMno())
                .message("")
                .build();
        if (result.isReset()) {
            loginServiceDTO.setMessage("password 변경이 필요 합니다");
        }

        return loginServiceDTO;
    }

    //회원가입
    @Override
    public Long signup(SignupRequestDTO signupDTO) throws Exception{

        // email 중복처리
        if(memberMapper.existEmail(signupDTO.getEmail())==1){
            throw new RuntimeException("중복된 이메일");
        }

        Member member=signupDtoToEntity(signupDTO);
        // password encoding
        member.changePassword(passwordEncoder.encode(member.getPassword()));
        member.changeIsValidate(false);
        Long mno;
        try {
            mno=memberMapper.signUp(member);

            TokenDTO token = tokenService.generateTokens(mno);
            mailService.sendValidateUrl(signupDTO.getEmail(), signupDTO.getName(), token.getToken());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return mno;
    }


    //아직 테스트 X
    //이메일 확인
    @Override
    public int emailCheck(String email) {
        return memberMapper.existEmail(email);
    }


    //이메일 찾기
    @Override
    public String findEmail(String name, String phone) throws AccountNotFoundException {
//        Optional<Member> result = memberRepository.findMemberByNameAndPhone(name, phone);
        Member member=memberMapper.searchUserFromNameAndPhone(name,phone);
        // DB에 없는 경우
//        if (result.isEmpty()) {
//            throw new AccountNotFoundException("not found member");
//        }
//
//        Member member = result.get();

        if(member==null){
            throw new AccountNotFoundException("not found member");
        }
        return member.getEmail();
    }

    //비밀번호 변경
    @Override
    public ResponseDTO changePassword(ChangePasswordRequestDTO passwordRequestDTO) {
//        Optional<Member> result = memberRepository.findByEmailAndFromSocialIsFalse(passwordRequestDTO.getEmail());
        Member member=memberMapper.findMemberFromEmail(passwordRequestDTO.getEmail(),false);
        ResponseDTO responseDTO;

        // DB 없을 경우
        if (member==null) {
            throw new BadCredentialsException("유저 정보가 없습니다");
        }
//        Member member = result.get();

        // 기존 비밀번호가 일치하지 않을 경우
        if (!passwordEncoder.matches(passwordRequestDTO.getOldPassword(), member.getPassword())) {
            throw new BadCredentialsException("Password가 일치하지 않습니다");
        }

        String newPassword = passwordEncoder.encode(passwordRequestDTO.getNewPassword());
//        member.changePassword(newPassword);
//        member.changeIsReset(false);

        //매퍼 만들어야함
        try {
//            memberRepository.save(member);
            memberMapper.changePassword(member.getMno(),newPassword,false);
            responseDTO = ResponseDTO.builder()
                    .msg("change success")
                    .result(true)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return responseDTO;
    }

    // 비밀번호 초기화
    @Override
    public ResponseDTO resetPassword(EmailRequestDTO emailRequestDTO) {
//        Optional<Member> result = memberRepository.findByEmailAndFromSocialIsFalse(emailRequestDTO.getEmail());

        Member member=memberMapper.findMemberFromEmail(emailRequestDTO.getEmail(),false);
        // 검색결과x
        if (member==null) {
            return ResponseDTO.builder()
                    .msg("not found member")
                    .result(false)
                    .build();
        }
//        Member member = result.get();
        //이전 비밀번호
        String oldPassword = member.getPassword();
        //새로운 초기화 비밀번호
        String password = generateRandomPassword();

        //member.changeIsReset(true); // 비밀번호 변경 요청 여부 변경
        //member.changePassword(passwordEncoder.encode(password));

        //수정하는것 매퍼 추가해야함
        try {
//            memberRepository.save(member);
            memberMapper.changePassword(member.getMno(),passwordEncoder.encode(password),true);
            mailService.sendPassword(member.getEmail(), member.getName(), password);
        } catch (Exception e) {
            log.error(e.getClass());
            if (e instanceof MailSendException) {
//                member.changePassword(oldPassword);
//                memberRepository.save(member);
                memberMapper.changePassword(member.getMno(), oldPassword,false);
                return ResponseDTO.builder()
                        .msg("email transfer failed")
                        .result(false)
                        .build();
            } else {
                return ResponseDTO.builder()
                        .msg("change failed")
                        .result(false)
                        .build();
            }
        }

        return ResponseDTO.builder()
                .msg("success")
                .result(true)
                .build();
    }

    //랜덤 비밀번호 생성기
    private String generateRandomPassword() {
        final int pwLength = 12;
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[pwLength];
        secureRandom.nextBytes(randomBytes);

        String password = Base64.getEncoder().encodeToString(randomBytes);

        password = password.substring(0, pwLength);

        return password;
    }

    //mapper 추가해야함
    @Override
    public Boolean checkValidate(String email) {
//        Optional<Member> result = memberRepository.findByEmail(email);
        Member member=memberMapper.findMemberFromEmail(email,false);

        if (member==null) {
            return false;
        }
//        Member member = result.get();

        if (member.isValidate()){
            return false;
        }
//        member.changeIsValidate(true);
//        memberRepository.save(member);
        memberMapper.emailValidate(email);

        return true;
    }

    //로그아웃
    @Override
    public void logout() {
        Long mno = SecurityUtil.getCurrentMemberMno();
        tokenService.deleteRefreshToken(mno);
    }

    //
    @Override
    public void resendEmail(String email) throws Exception{
//        Optional<Member> result = memberRepository.findByEmailAndFromSocialIsFalse(email);
        Member member=memberMapper.findMemberFromEmail(email,false);
        if (member==null) {
            throw new BadCredentialsException("존재하지 않는 이메일");
        }

//        Member member = result.get();
        if (member.isValidate()) {
            throw new IllegalAccessException("이미 인증된 이메일 입니다.");
        }

        try {
            TokenDTO token = tokenService.generateTokens(member.getMno());
            mailService.reSendValidateUrl(member.getEmail(), member.getName(), token.getToken());
        } catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
}
