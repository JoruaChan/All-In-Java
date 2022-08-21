package cn.joruachan.study.springweb.deferredresult;

import cn.joruachan.study.springweb.WebResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
@RestController
@RequestMapping("/deferredResult")
public class DeferredResultController {

    /**
     * DeferredResult，用一个Map存着
     */
    private final Map<String, DeferredResult<WebResponse<String>>> deferredResultMap =
            new ConcurrentHashMap<>();

    @PostMapping("/{tryId}")
    public DeferredResult<WebResponse<String>> deferredResult(@PathVariable("tryId") String tryId) {
        DeferredResult<WebResponse<String>> deferredResult = new DeferredResult<>();
        deferredResultMap.put(tryId, deferredResult);
        return deferredResult;
    }

    @PutMapping("/{tryId}")
    public WebResponse<String> updateDeferredResult(@PathVariable("tryId") String tryId) {
        WebResponse<String> webResponse = new WebResponse<>();

        DeferredResult<WebResponse<String>> deferredResult = deferredResultMap.get(tryId);
        if (deferredResult == null) {
            webResponse.setCode(404);
            webResponse.setMessage("Not existed TryId!");
        } else {
            webResponse.setCode(200);

            WebResponse<String> drResponse = new WebResponse<>();
            drResponse.setCode(200);
            drResponse.setMessage("Good job!");
            drResponse.setObject("Fighting!");
            deferredResult.setResult(drResponse);
        }

        return webResponse;
    }

}
