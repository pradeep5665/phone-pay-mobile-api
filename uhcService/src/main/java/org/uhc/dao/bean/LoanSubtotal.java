/* 
 * ===========================================================================
 * File Name LoanSubtotal.java
 * 
 * Created on Jul 17, 2018
 *
 * This code contains copyright information which is the proprietary property
 * of Chetu India Pvt. Ltd. No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of CHETU.
 *
 * Copyright (C) CHETU. 2018
 * All rights reserved.
 *
 * Modification history:
 * $Log: LoanSubtotal.java,v $
 * ===========================================================================
 */
package org.uhc.dao.bean;

import java.math.BigDecimal;

/**
 * @author nehas3
 * @date Jul 17, 2018
 * @Description : This is LoanSubtotal bean to get sub total of loan amount.
 */
public class LoanSubtotal {

	private long loanNumber;
    private BigDecimal subtotal;
    
    public LoanSubtotal(long loanNumber, BigDecimal subtotal) {
        this.loanNumber = loanNumber;
        this.subtotal = subtotal;
    }
    
    public long getLoanNumber() {
        return loanNumber;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}
