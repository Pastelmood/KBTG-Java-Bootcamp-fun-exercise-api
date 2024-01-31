package com.javabootcamp.fintechbank.accounts;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/accounts")
@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "list all accounts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "list all accounts",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = AccountResponse.class)))
                    })
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<AccountResponse> getAccounts() {
        return accountService.getAccounts();
    }

    @Operation(summary = "deposits an amount to the account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "deposits money from specific account",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountResponse.class))
                    })
    })
    @RequestMapping(value = "/{accountNo}/deposit", method = RequestMethod.POST)
    public AccountResponse depositAccount(
            @PathVariable(name = "accountNo") Integer accountNo,
            @RequestBody @Valid DepositRequest depositRequest
    ) {
        return accountService.depositAccount(accountNo, depositRequest);
    }


    @Operation(summary = "จนมาเห็นกับตา จนพาใจมาเจ็บ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ฉีกบ่มีหม่องเย็บ หัวใจที่ให้เจ้า",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountResponse.class))
                    })
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public AccountResponse createAccount(@RequestBody @Valid CreateAccountRequest createAccountRequest) {
        return accountService.createAccount(createAccountRequest);
    }



    @Operation(summary = "บักคนซั่วจั่งอ้าย มันเอาเหล้ายาปลาปิ้งเป็นใหญ่")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "มันบ่เหมาะกับไผไคแนแต่ไปเลาะหาเซ็นเหล้า",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountResponse.class))
                    })
    })
    @RequestMapping(value = "/{accountNo}/withdraw", method = RequestMethod.POST)
    public AccountResponse withdrawAccount(
            @PathVariable(name = "accountNo") Integer accountNo,
            @RequestBody @Valid WithdrawRequest withdrawRequest
    ) {
        return accountService.withdrawAccount(accountNo, withdrawRequest);
    }



    @Operation(summary = "แม่ฮ้างมหาเสน่ห์")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "...",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountResponse.class))
                    })
    })
    @RequestMapping(value = "/{accountNo}/transfer/{targetAccountNo}", method = RequestMethod.POST)
    public AccountResponse transferBalance(
            @PathVariable(name = "accountNo") Integer accountNo,
            @PathVariable(name = "targetAccountNo") Integer targetAccountNo,
            @RequestBody @Valid TransferRequest transferRequest
    ) {
        return accountService.transferBalance(accountNo, targetAccountNo, transferRequest);
    }



    @Operation(summary = "My sugar daddy, หมดใจเลยที่ฟ้าให้พ่อ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "รักจริงไม่ได้หลอก แค่อยากจะขอให้พ่อช่วยฟ้าหน่อย",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountResponse.class))
                    })
    })
    @RequestMapping(value = "/{accountNo}", method = RequestMethod.GET)
    public AccountResponse getAccount(
            @PathVariable(name = "accountNo") Integer accountNo
    ) {
        return accountService.getAccountById(accountNo);
    }


}

