package com.myPackage.rest.mvc;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import com.myPackage.core.email.EmailHtmlSender;
import com.myPackage.core.email.EmailSender;
import com.myPackage.core.entities.Account;
import com.myPackage.core.entities.PlaneTicket;
import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.services.AccountService;
import com.myPackage.core.services.PlaneTicketOrderService;
import com.myPackage.core.services.TrainTicketOrderService;
import com.myPackage.core.services.exceptions.AccountAlreadyExistsException;
import com.myPackage.core.services.exceptions.AccountDoesNotExistException;
import com.myPackage.core.services.exceptions.PlaneTicketNotFoundException;
import com.myPackage.core.services.util.AccountList;
import com.myPackage.core.services.util.PlaneTicketOrderList;
import com.myPackage.core.services.util.TrainTicketOrderList;
import com.myPackage.rest.exceptions.BadRequestException;
import com.myPackage.rest.exceptions.ConflictException;
import com.myPackage.rest.exceptions.NotFoundException;
import com.myPackage.rest.resources.AccountListResource;
import com.myPackage.rest.resources.AccountResource;
import com.myPackage.rest.resources.PlaneTicketOrderListResource;
import com.myPackage.rest.resources.PlaneTicketResource;
import com.myPackage.rest.resources.TrainTicketOrderListResource;
import com.myPackage.rest.resources.asm.AccountListResourceAsm;
import com.myPackage.rest.resources.asm.AccountResourceAsm;
import com.myPackage.rest.resources.asm.PlaneTicketOrderListResourceAsm;
import com.myPackage.rest.resources.asm.PlaneTicketResourceAsm;
import com.myPackage.rest.resources.asm.TrainTicketOrderListResourceAsm;
import com.myPackage.rest.validators.AccountValidator;

@RestController
@RequestMapping(value = "/rest/accounts")
public class AccountController {
	@Autowired
	private AccountService accountService;

	AccountValidator accountValidator;

	@Autowired
	PlaneTicketOrderService planeTicketOrderService;
	
	@Autowired
	TrainTicketOrderService trainTicketOrderService;
	
	@Autowired
	EmailSender emailSender;
	
	@Autowired
	EmailHtmlSender emailHtmlSender;
	
	public AccountController() {
		accountValidator = new AccountValidator();
	}

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
		accountValidator = new AccountValidator();
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(accountValidator);
	}
	
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AccountListResource> findAllAccounts(@RequestParam(value="login", required = false) String login) {
		try {

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
		} catch (AccountDoesNotExistException exception) {
			throw new NotFoundException(exception);
		}
    }
	
	@RequestMapping(value = "/myAccount",method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<AccountResource> getMyAccount() {
		AccountResource res;

		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!(auth instanceof AnonymousAuthenticationToken) && principal instanceof UserDetails) {//if user is logged in - has auth
				 UserDetails details = (UserDetails)principal;	
				 Account loggedIn = accountService.findAccountByLogin(details.getUsername());
						Account account = accountService.findAccount(loggedIn.getId());
						res = new AccountResourceAsm().toResource(account);
			}else{
				 throw new AccountDoesNotExistException(); 
			}
			return new ResponseEntity<AccountResource>(res, HttpStatus.OK);

		} catch (AccountDoesNotExistException exception) {
			throw new NotFoundException(exception);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
//	@PreAuthorize("permitAll")
	public ResponseEntity<AccountResource> createAccount(@Valid @RequestBody AccountResource sentAccount) {

        try {
            Account account = accountService.createAccount(sentAccount.toAccount());
            AccountResource resource = new AccountResourceAsm().toResource(account);
            HttpHeaders headers = new HttpHeaders();
            
            Context context = new Context();
            context.setVariable("title", "Welcome!");
            context.setVariable("description", "Your account in TickTwo service was created. Thank You!");
            
            		emailHtmlSender.send(account.getEmail(), "Title of email", "email-template", context);

            headers.setLocation(URI.create(resource.getLink("self").getHref()));
            return new ResponseEntity<AccountResource>(resource, headers, HttpStatus.CREATED);
        } catch(AccountAlreadyExistsException exception) {
            throw new ConflictException(exception);
        }
		
	}
	@RequestMapping(value = "/newAdmin", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<AccountResource> createAdminAccount(@Valid @RequestBody AccountResource sentAccount) {

        try {
            Account account = accountService.createAdminAccount(sentAccount.toAccount());
            AccountResource resource = new AccountResourceAsm().toResource(account);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(resource.getLink("self").getHref()));
            return new ResponseEntity<AccountResource>(resource, headers, HttpStatus.CREATED);
        } catch(AccountAlreadyExistsException exception) {
            throw new ConflictException(exception);
        }
		
	}
	
	@RequestMapping(value = "/{accountId}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<AccountResource> deleteAccount(@PathVariable("accountId") Long accountId) {
		try {
			
			// delete orders for account
			List<PlaneTicketOrder> planeTicketOrderList2 = planeTicketOrderService.findAllPlaneTicketOrders()
					.getPlaneTicketOrders();
			for (Iterator it3 = planeTicketOrderList2.iterator(); it3.hasNext();) {
				PlaneTicketOrder planeTicketOrder2 = (PlaneTicketOrder) it3.next();

				if(planeTicketOrder2.getOwner() != null){
					if (planeTicketOrder2.getOwner().getId().longValue() == accountId.longValue()) {

						planeTicketOrderService.deletePlaneTicketOrder(planeTicketOrder2.getId());

					}
				}


			}
			List<TrainTicketOrder> trainTicketOrderList2 = trainTicketOrderService.findAllTrainTicketOrders()
					.getTrainTicketOrders();
			for (Iterator it3 = trainTicketOrderList2.iterator(); it3.hasNext();) {
				TrainTicketOrder trainTicketOrder2 = (TrainTicketOrder) it3.next();
				if (trainTicketOrder2.getOwner() != null) {

					if (trainTicketOrder2.getOwner().getId().longValue() == accountId.longValue()) {

						trainTicketOrderService.deleteTrainTicketOrder(trainTicketOrder2.getId());

					}
				}

			}			
			Account account = accountService.deleteAccount(accountId);
			AccountResource res = new AccountResourceAsm().toResource(account);
			return new ResponseEntity<AccountResource>(res, HttpStatus.OK);

		} catch (AccountDoesNotExistException exception) {
			throw new NotFoundException(exception);
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
//	@RequestMapping(value = "/{accountId}/planeTicketOrders", method = RequestMethod.GET)
//	public ResponseEntity<PlaneTicketOrderListResource> findAllPlaneTicketOrdersForAccount(@PathVariable Long accountId) {
//        try {
//
//			PlaneTicketOrderList planeTicketOrderList = accountService.findAllPlaneTicketOrdersForAccount(accountId);
//			PlaneTicketOrderListResource planeTicketOrderListResource = new PlaneTicketOrderListResourceAsm().toResource(planeTicketOrderList);
//			return new ResponseEntity<PlaneTicketOrderListResource>(planeTicketOrderListResource, HttpStatus.OK);
//
//        } catch(AccountDoesNotExistException exception)
//        {
//            throw new BadRequestException(exception);
//        }
//		
//	}
//	@RequestMapping(value = "/{accountId}/trainTicketOrders", method = RequestMethod.GET)
//	public ResponseEntity<TrainTicketOrderListResource> findAllTrainTicketOrdersForAccount(@PathVariable Long accountId) {
//        try {
//
//			TrainTicketOrderList trainTicketOrderList = accountService.findAllTrainTicketOrdersForAccount(accountId);
//			TrainTicketOrderListResource planeTicketOrderListResource = new TrainTicketOrderListResourceAsm().toResource(trainTicketOrderList);
//	
//			return new ResponseEntity<TrainTicketOrderListResource>(planeTicketOrderListResource, HttpStatus.OK);
//        } catch(AccountDoesNotExistException exception)
//        {
//            throw new BadRequestException(exception);
//        }
//	}

}
