package com.myPackage.core.services;


import com.myPackage.core.entities.Account;
import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.services.util.AccountList;
import com.myPackage.core.services.util.PlaneTicketOrderList;
import com.myPackage.core.services.util.TrainTicketOrderList;

public interface AccountService {
    public Account findAccount(Long id);
    public Account findAccountByLogin(String login);
    public AccountList findAllAccounts();
	public PlaneTicketOrder createPlaneTicketOrderForAccount(Long accountId, PlaneTicketOrder data);
    public TrainTicketOrder createTrainTicketOrderForAccount(Long accountId, TrainTicketOrder data);
    public PlaneTicketOrderList findAllPlaneTicketOrdersForAccount(Long accountId);
    public TrainTicketOrderList findAllTrainTicketOrdersForAccount(Long accountId);
    public Account createAccount(Account data);
    public Account deleteAccount(Long id);

//    public PlaneTicketOrder createPlaneticketOrder(Long )


}
