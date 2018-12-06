package web.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.model.Result;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {
    private String code;
    private List<String> fileName;
    private String error;

    public ResultDTO(Result result){
        this.code = result.getCode();
        fileName = Arrays.stream(result.getFileNames().split(",")).collect(Collectors.toList());
        error = result.getError();
    }
}
