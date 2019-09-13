package net.sunxu.website.help.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResultDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    private boolean success;

    private String message;

    public static ResultDTO success() {
        var dto = new ResultDTO();
        dto.setSuccess(true);
        dto.setMessage("");
        return dto;
    }

    public static ResultDTO success(String message, Object... paras) {
        var dto = new ResultDTO();
        dto.setSuccess(true);
        dto.setMessage(paras.length > 0 ? String.format(message, paras) : message);
        return dto;
    }

    public static ResultDTO fail(String message, Object... paras) {
        var dto = new ResultDTO();
        dto.setSuccess(false);
        dto.setMessage(paras.length > 0 ? String.format(message, paras) : message);
        return dto;
    }

}
