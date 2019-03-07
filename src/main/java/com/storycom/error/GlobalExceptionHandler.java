package com.storycom.error;

import com.storycom.base.Base;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends Base {

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleException(Exception ex, HttpServletRequest request) {
        log.error("Exception occured!", ex);

        String errorMsg;

        if (isDBConnectError(ex))
        {
            log.debug("Database exception!");
            errorMsg = getMessage("error.dbconnect");
        }
        else
        {
            log.debug("Other exception!");
            errorMsg = getMessage("error.system");
        }

        ModelAndView mv = new ModelAndView("error/error");
        mv.addObject("msgError", errorMsg);
        return mv;
    }
}
