package com.sparta.plusboard.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.plusboard.global.jwt.JwtUtil;
import com.sparta.plusboard.global.exception.CommonResponseDto;
import com.sparta.plusboard.user.dto.LoginRequestDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword()
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        String token = jwtUtil.createToken(username);//토큰 생성
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);//발급한 토큰을 Header에 추가

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        CommonResponseDto responseDto = new CommonResponseDto("로그인에 성공하였습니다.", HttpStatus.OK.value());
        PrintWriter writer = response.getWriter();
        writer.println(new ObjectMapper().writeValueAsString(responseDto));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);//추가적으로 다른 것 보내도됨!
        response.setCharacterEncoding("UTF-8");//한글 깨짐 방지
        response.setContentType("application/json");

        CommonResponseDto responseDto = new CommonResponseDto("닉네임 또는 패스워드를 확인해주세요.", HttpStatus.BAD_REQUEST.value());
        PrintWriter writer = response.getWriter();
        writer.println(new ObjectMapper().writeValueAsString(responseDto));
    }
}
