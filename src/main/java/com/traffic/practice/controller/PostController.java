package com.traffic.practice.controller;

import com.traffic.practice.aop.LoginCheck;
import com.traffic.practice.dto.PostDTO;
import com.traffic.practice.dto.UserDTO;
import com.traffic.practice.dto.response.CommonResponse;
import com.traffic.practice.service.PostService;
import com.traffic.practice.service.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final UserService userService;
    private final PostService postService;

    public PostController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse> register(String accountId,
                                                   @RequestBody PostDTO postDTO) {
        postService.register(accountId, postDTO);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "200", "게시글 등록 성공", postDTO);

        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping("/my-posts")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<List<PostDTO>>> MyPostList(String accountId) {
        UserDTO userDto = userService.getUserProfile(accountId);
        List<PostDTO> postDTOList = postService.getMyPostList(userDto.getId());
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "200", "게시글 조회 성공", postDTOList);

        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("/{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostResponse>> updatePost(String accountId,
                                                                   @PathVariable Long postId,
                                                                   @RequestBody PostRequest postRequest) {
        UserDTO memberInfo = userService.getUserProfile(accountId);
        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .name(postRequest.getName())
                .categoryId(postRequest.getCategoryId())
                .contents(postRequest.getContents())
                .fileId(postRequest.getFileId())
                .userId(memberInfo.getId())
                .updateTime(new Date())
                .views(postRequest.getViews())
                .build();
        postService.updatePost(postDTO);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "200", "게시글 수정 성공", postDTO);

        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("/{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDeleteRequest>> deletePost(String accountId,
                                                                        @PathVariable(name = "postId") Long postId,
                                                                        @RequestBody PostDeleteRequest postDeleteRequest) {
        UserDTO memberInfo = userService.getUserProfile(accountId);
        postDeleteRequest.setAccountId(memberInfo.getId());
        postDeleteRequest.setId(postId);
        postService.deletePost(memberInfo.getId(), postId);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "200", "게시글 삭제 성공", postDeleteRequest);

        return ResponseEntity.ok(commonResponse);
    }

    @Getter
    @NoArgsConstructor
    private static class PostResponse {
        private List<PostDTO> postDTOList;
    }

    @Getter
    @NoArgsConstructor
    private static class PostRequest {
        private String name;
        private String contents;
        private int views;
        private Long categoryId;
        private Long userId;
        private Long fileId;
        private Date updateTime;
    }

    @Getter
    @Setter
    private static class PostDeleteRequest {
        private Long accountId;
        private Long id;
    }
}



