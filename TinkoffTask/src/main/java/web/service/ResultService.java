package web.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dto.ResultDTO;
import web.enums.Codes;
import web.model.Result;
import web.repository.ResultRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@ShellComponent
@AllArgsConstructor
@Log4j
public class ResultService {

    private final ResultRepository resultRepository;

    @Autowired
    FindindService findindService;

    /**
     * Метод для поиска числа в файлах
     *
     * @param number - число
     * @return Для тестирования использовал shell
     */
    @Transactional
    @ShellMethod("find numbers from files")
    public ResultDTO findNumber(Integer number) {

        List<String> fileNames = new ArrayList<>();

        Codes globalCodes = Codes.NOT_FOUND;

        long start = System.currentTimeMillis();

        for (int i = 0; i < 20; i++) {
            String fileName = i + ".txt";
            Codes codes = findindService.isNumberFromFile(number, fileName);
            if (Codes.OK == codes) {
                fileNames.add(fileName);
            }
            if (Codes.OK == codes && globalCodes != Codes.ERROR) {
                globalCodes = codes;
            }
            if (Codes.ERROR == codes) globalCodes = codes;
        }

        Result result = new Result();
        result.setId(UUID.randomUUID());
        result.setCode(globalCodes.getDesk());
        result.setNumber(number);
        result.setFileNames(fileNames.stream().collect(Collectors.joining(",")));
        result.setError(globalCodes.getError());
        result = resultRepository.save(result);

        long time = System.currentTimeMillis() - start;
        log.info("Time in work(seconds): " + time / 1e3);

        return new ResultDTO(result);
    }

}
