package com.racegame.character.bidder.action;

import com.racegame.character.bidder.Bidder;

public interface BidderActionStrategy {
    void execute();
    void displayResult();
    String getDetails();
    void setActingBidder(Bidder bidder);
}
