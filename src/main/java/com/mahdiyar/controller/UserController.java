package com.mahdiyar.controller;

import com.mahdiyar.context.RequestContext;
import com.mahdiyar.exceptions.ServiceException;
import com.mahdiyar.model.dto.user.LoginRequestDto;
import com.mahdiyar.model.dto.user.LoginResponseDto;
import com.mahdiyar.model.dto.user.SignupRequestDto;
import com.mahdiyar.model.dto.user.UserDto;
import com.mahdiyar.model.dto.workspace.WorkspaceDto;
import com.mahdiyar.security.AuthRequired;
import com.mahdiyar.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author mahdiyar
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RequestContext requestContext;

    @ApiOperation("Signup")
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequestDto requestDto)
            throws ServiceException {
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("Login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto requestDto,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        return ResponseEntity.ok(userService.login(requestDto, request, response));
    }

    @ApiOperation("Get Users List")
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @ApiOperation("Get User Workspaces")
    @GetMapping("/workspaces")
    @AuthRequired
    public ResponseEntity<List<WorkspaceDto>> getUserWorkspaces() {
        return ResponseEntity.ok(userService.getUserWorkspaces(requestContext.getUser()));
    }
}
