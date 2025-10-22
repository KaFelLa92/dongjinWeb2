package web2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // [1] 관리자 메인페이지 요청 예제
    // URL : http://localhost:8080/api/admin/dashboard
    // "ADMIN" 권한이 있을 경우 반환되고, 없을 경우 403이 뜸(권한없음)
    @GetMapping("/dashboard")
    public String dashboard( ){
        return "관리자 API 요청 성공";
    }
}
