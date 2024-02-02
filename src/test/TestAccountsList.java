package test;

import util.AccountsList;
import valorant.Account;
import valorant.Currency;

public class TestAccountsList {

    public static void main(String[] args) {
        testSortByName();
    }

    public static void testSortByName() {
        AccountsList list = new AccountsList();
        var acc1 = new Account("id1", "pw1", "f_name", "222", Currency.OTHER);
        var acc2 = new Account("id1", "pw1", "e_name", "222", Currency.OTHER);
        var acc3 = new Account("id1", "pw1", "a_name", "222", Currency.OTHER);
        list.add(acc1);
        list.add(acc2);
        list.add(acc3);
        var oldListSize = list.size();
        System.out.println(list.size());
        for(var a : list) {
            System.out.print(a.name() + ", ");
        }
        System.out.println();
        list.sortByName();
        System.out.println(list.size());
        for(var a : list) {
            System.out.print(a.name() + ", ");
        }
        assert(oldListSize == list.size());
    }

}
