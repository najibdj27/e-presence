package com.unper.samper.controller;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unper.samper.exception.PasswordNotMatchException;
import com.unper.samper.exception.ResourceNotFoundException;
import com.unper.samper.exception.SignInFailException;
import com.unper.samper.exception.WrongOTPException;
import com.unper.samper.model.dto.ConfirmOTPRequestDto;
import com.unper.samper.model.dto.ForgetPasswordRequestDto;
import com.unper.samper.model.dto.ResetPasswordRequestDto;
import com.unper.samper.model.dto.SignInRequestDto;
import com.unper.samper.service.impl.AuthenticationServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "1. Auth Controller")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationServiceImpl authenticationServiceImpl;

    /***
     * Sign in and get the token for access
     * @param signInRequest
     * @return
     * @throws SignInFailException
     */
    @Operation(summary = "Sign in and get the token for access")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@Valid @RequestBody SignInRequestDto requestDto) throws SignInFailException {
        return authenticationServiceImpl.authenticateUser(requestDto);
    }

    /***
     * Forget password
     * @param forgetPasswordRequestDTO
     * @return
     * @throws ResourceNotFoundException
     * @throws MessagingException
     */
    @Operation(summary = "Get OTP to reset password")
    @PostMapping("/forgetpassword")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordRequestDto requestDto) throws ResourceNotFoundException, MessagingException {
        return authenticationServiceImpl.changePassword(requestDto);
    }

    /***
     * Confirm OTP
     * @param confirmOTPRequestDTO
     * @return
     * @throws WrongOTPException
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Confirm OTP to get the token to reset the password")
    @PostMapping("/confirmotp")
    public ResponseEntity<?> confirmOTP(@Valid @RequestBody ConfirmOTPRequestDto requestDto) throws WrongOTPException, ResourceNotFoundException {
        return authenticationServiceImpl.confirmOTP(requestDto);
    }

    /***
     * Reset password
     * @param token
     * @param resetPasswordRequestDTO
     * @return
     * @throws PasswordNotMatchException
     * @throws ResourceNotFoundException
     */
    @Operation(summary = "Reset the password")
    @PatchMapping("/reset_password")
    public ResponseEntity<?> resetPassword(@RequestParam("token") UUID token, @Valid @RequestBody ResetPasswordRequestDto requestDto) throws PasswordNotMatchException, ResourceNotFoundException {
        return authenticationServiceImpl.resetPassword(token, requestDto);
    }
}