package net.sunxu.website.help.webutil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TestApplication {

    @Autowired
    private MockableService mockableService;

    @GetMapping("/")
    public Object executeInWeb() {
        return mockableService.execute();
    }
}
