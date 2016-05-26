package com.myPackage.rest.mvc;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myPackage.core.entities.Account;
import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.services.AccountService;
import com.myPackage.core.services.exceptions.AccountAlreadyExistsException;
import com.myPackage.core.services.exceptions.AccountDoesNotExistException;
import com.myPackage.core.services.exceptions.PlaneTicketOrderAlreadyExistsException;
import com.myPackage.core.services.exceptions.TrainTicketOrderAlreadyExistsException;
import com.myPackage.core.services.util.AccountList;
import com.myPackage.core.services.util.PlaneTicketOrderList;
import com.myPackage.core.services.util.TrainTicketOrderList;
import com.myPackage.rest.exceptions.BadRequestException;
import com.myPackage.rest.exceptions.ConflictException;
import com.myPackage.rest.resources.AccountListResource;
import com.myPackage.rest.resources.AccountResource;
import com.myPackage.rest.resources.PlaneTicketOrderListResource;
import com.myPackage.rest.resources.PlaneTicketOrderResource;
import com.myPackage.rest.resources.TrainTicketOrderListResource;
import com.myPackage.rest.resources.TrainTicketOrderResource;
import com.myPackage.rest.resources.asm.AccountListResourceAsm;
import com.myPackage.rest.resources.asm.AccountResourceAsm;
import com.myPackage.rest.resources.asm.PlaneTicketOrderListResourceAsm;
import com.myPackage.rest.resources.asm.PlaneTicketOrderResourceAsm;
import com.myPackage.rest.resources.asm.TrainTicketOrderListResourceAsm;
import com.myPackage.rest.resources.asm.TrainTicketOrderResourceAsm;

@RestController
@RequestMapping(value = "/rest/accounts")
public class AccountController {
	@Autowired
	private AccountService accountService;


	public AccountController() {
	}

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<AccountListResource> findAllAccounts(@RequestParam(value="login", required = false) String login) {
        AccountList list = null;
        if(login == null) {
            list = accountService.findAllAccounts();
        } else {
            Account account = accountService.findAccountByLogin(login);
            if(account == null) {
                list = new AccountList(new ArrayList<Account>());
            } else {
                list = new AccountList(Arrays.asList(account));
            }
        }
        AccountListResource res = new AccountListResourceAsm().toResource(list);
        return new ResponseEntity<AccountListResource>(res, HttpStatus.OK);
    }
	

	@RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
	public ResponseEntity<AccountResource> getAccount(@PathVariable Long accountId) {
		Account account = accountService.findAccount(accountId);
		if (account != null) {
			AccountResource res = new AccountResourceAsm().toResource(account);
			return new ResponseEntity<AccountResource>(res, HttpStatus.OK);
		} else {
			return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<AccountResource> createAccount(@RequestBody AccountResource sentAccount) {

        try {
            Account account = accountService.createAccount(sentAccount.toAccount());
            AccountResource resource = new AccountResourceAsm().toResource(account);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(resource.getLink("self").getHref()));
            return new ResponseEntity<AccountResource>(resource, headers, HttpStatus.CREATED);
        } catch(AccountAlreadyExistsException exception) {
            throw new ConflictException(exception);
        }
		
	}
//    @RequestMapping(value="/{accountId}/planeTicketOrders",
//            method = RequestMethod.POST)
//        public ResponseEntity<PlaneTicketOrderResource> createPlaneTicketOrder(
//                @PathVariable Long accountId,
//                @RequestBody PlaneTicketOrderResource resource)
//        {
//            try {
//                PlaneTicketOrder createdPlaneTicketOrder = accountService.createPlaneTicketOrderForAccount(accountId, resource.toPlaneTicketOrder());
//                PlaneTicketOrderResource createdPlaneTicketOrderResource = new PlaneTicketOrderResourceAsm().toResource(createdPlaneTicketOrder);
//                HttpHeaders headers = new HttpHeaders();
//                headers.setLocation(URI.create(createdPlaneTicketOrderResource.getLink("self").getHref()));
//                return new ResponseEntity<PlaneTicketOrderResource>(createdPlaneTicketOrderResource, headers, HttpStatus.CREATED);
//            } catch(AccountDoesNotExistException exception)//service layer exception
//            {
//                throw new BadRequestException(exception);//rest layer exception
//            }catch(PlaneTicketOrderAlreadyExistsException ex){
//            	throw new ConflictException(ex);
//            }
//        }
//    @RequestMapping(value="/{accountId}/trainTicketOrders",
//            method = RequestMethod.POST)
//        public ResponseEntity<TrainTicketOrderResource> createTrainTicketOrder(@PathVariable Long accountId,
//                @RequestBody TrainTicketOrderResource resource)
//        {
//            try {
//            	TrainTicketOrder createdTrainTicketOrder = accountService.createTrainTicketOrderForAccount(accountId, resource.toTrainTicketOrder());
//                TrainTicketOrderResource createdTrainTicketOrderResource = new TrainTicketOrderResourceAsm().toResource(createdTrainTicketOrder);
//                HttpHeaders headers = new HttpHeaders();
//                headers.setLocation(URI.create(createdTrainTicketOrderResource.getLink("self").getHref()));
//                return new ResponseEntity<TrainTicketOrderResource>(createdTrainTicketOrderResource, headers, HttpStatus.CREATED);
//            } catch(AccountDoesNotExistException exception)
//            {
//                throw new BadRequestException(exception);
//            }catch(TrainTicketOrderAlreadyExistsException ex){
//            	throw new ConflictException(ex);
//            }
//        }
	@RequestMapping(value = "/{accountId}/planeTicketOrders", method = RequestMethod.GET)
	public ResponseEntity<PlaneTicketOrderListResource> findAllPlaneTicketOrdersForAccount(@PathVariable Long accountId) {
        try {

			PlaneTicketOrderList planeTicketOrderList = accountService.findAllPlaneTicketOrdersForAccount(accountId);
			PlaneTicketOrderListResource planeTicketOrderListResource = new PlaneTicketOrderListResourceAsm().toResource(planeTicketOrderList);
			return new ResponseEntity<PlaneTicketOrderListResource>(planeTicketOrderListResource, HttpStatus.OK);

        } catch(AccountDoesNotExistException exception)
        {
            throw new BadRequestException(exception);
        }
		
	}
	@RequestMapping(value = "/{accountId}/trainTicketOrders", method = RequestMethod.GET)
	public ResponseEntity<TrainTicketOrderListResource> findAllTrainTicketOrdersForAccount(@PathVariable Long accountId) {
        try {

			TrainTicketOrderList trainTicketOrderList = accountService.findAllTrainTicketOrdersForAccount(accountId);
			TrainTicketOrderListResource planeTicketOrderListResource = new TrainTicketOrderListResourceAsm().toResource(trainTicketOrderList);
	
			return new ResponseEntity<TrainTicketOrderListResource>(planeTicketOrderListResource, HttpStatus.OK);
        } catch(AccountDoesNotExistException exception)
        {
            throw new BadRequestException(exception);
        }
	}

}
