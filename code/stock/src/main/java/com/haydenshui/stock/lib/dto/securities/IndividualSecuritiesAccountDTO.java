package com.haydenshui.stock.lib.dto.securities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class IndividualSecuritiesAccountDTO extends SecuritiesAccountDTO {
    
    /**
     * Gender of the individual account holder.
     */
    private String individualGender;

    /**
     * Occupation of the individual account holder.
     */
    private String individualOccupation;

    /**
     * Education level of the individual account holder.
     */
    private String individualEducation;

    /**
     * Employer of the individual account holder.
     */
    private String individualEmployer;

    public IndividualSecuritiesAccountDTO() { 
        super(); 
    }
    
}
