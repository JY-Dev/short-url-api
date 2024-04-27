package org.jydev.shorturlapi.presentation

import org.jydev.shorturlapi.app.SearchShortUrlUseCase
import org.jydev.shorturlapi.presentation.ShortUrlApiController.Companion.SHORT_URL_BASE
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.view.RedirectView

@Controller
class ShortUrlRedirectController(
    private val searchShortUrlUseCase: SearchShortUrlUseCase
) {

    @GetMapping("/${SHORT_URL_BASE}/{shortUrlPath}")
    fun getRealUrl(@PathVariable shortUrlPath : String) =
        RedirectView(searchShortUrlUseCase.invoke(shortUrlPath = shortUrlPath))
}