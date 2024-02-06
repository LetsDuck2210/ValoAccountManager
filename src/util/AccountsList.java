package util;

import valorant.Account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AccountsList extends ArrayList<Account> {
    private AccountsList customOrder;
    private boolean custom = true;
    public AccountsList() {
        super();
    }

    public AccountsList(AccountsList list) {
        super(list);
        customOrder = list.customOrder;
        custom = list.custom;
    }
    public void sortByName() {
        if(custom) {
            customOrder = new AccountsList(this);
            custom = false;
        }
        this.sort(Comparator.comparing(Account::name));
    }

    // TODO: the getRR() method needs to be changed to return the absolute RR, not x/100 in current rank
    public void sortByRank() {
        if(custom) {
            customOrder = new AccountsList(this);
            custom = false;
        }
        this.sort(Comparator.comparingInt(Account::getAbsoluteRR));
        reverse();
    }

    /**
     * Changes the order back to the custom order.
     */
    public void revertSort() {
        if(!custom) {
            this.removeAll(this);
            this.addAll(customOrder);
            custom = true;
        }
    }

    /**
     * This method will change the order of the accounts in the list. The given account will be set to the given index.
     * All following Accounts will shift by one index.
     * @param account account to move
     * @param index where to place the account, starting at 0
     */
    public void move(Account account, int index) {
        if(index > this.size() - 1) throw new IndexOutOfBoundsException();
        if(account == null) throw new IllegalArgumentException("no account provided");
        // TODO: maybe need to change to look for equal accounts, not identical
        if(!this.contains(account)) throw new IllegalArgumentException("account does not exist in list. Add it first.");
        this.remove(account);
        this.add(index, account);
    }

    /**
     * Reverses the list without changing the value the list is sorted by
     */
    public void reverse() {
        Collections.reverse(this);
    }

    /**
     * Returns the index of the given account in the list. If the account is not in the list, an
     * IllegalArgumentException is thrown
     * @param account the account to get the index from
     * @return the index
     */
    public int getIndex(Account account) {
        for(int i = 0; i < this.size(); i++) {
            if(account.equals(this.get(i))) return i;
        }
        throw new IllegalArgumentException();
    }
}
