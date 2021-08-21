package com.agriness.libraryrental.controller;

import com.agriness.libraryrental.dto.RentalBookSummaryDto;
import com.agriness.libraryrental.service.RentalBookService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/clients")
public class ClientController {

    private RentalBookService service;

    @GetMapping("/{clientId}/books/")
    @RolesAllowed({"user", "library-rental"})
    @ApiImplicitParam(name = "Authorization",
            value = "JWT Token",
            required = true,
            paramType = "header",
            example = "Bearer access_token")
    @ApiOperation(value = "Retorna o historico de livros do cliente pelo id.",
            notes = "Os dados existentes de clientes são: \n" +
                    "<p>| ID  | Nome   |</p>\n" +
                    "<p>|  1  | Paulo  |</p>\n" +
                    "<p>|  2  | Maria  |</p>\n" +
                    "<p>|  3  | Betina |</p>")
    public ResponseEntity<List<RentalBookSummaryDto>> reserveBook(@PathVariable Long clientId) {
        List<RentalBookSummaryDto> rentalBooksDtos = service.getRentalBooksByClientId(clientId);
        return ResponseEntity.ok(rentalBooksDtos);
    }
}
