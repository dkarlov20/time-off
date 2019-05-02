package com.khneu.timeoff.mail.factory;

import com.khneu.timeoff.mail.factory.impl.*;
import com.khneu.timeoff.model.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MailFactory {

    @Autowired
    private BereavementLeaveMail bereavementLeaveMail;

    @Autowired
    private BloodDonationMail bloodDonationMail;

    @Autowired
    private CompensatoryLeaveMail compensatoryLeaveMail;

    @Autowired
    private FullyPaidSickLeaveMail fullyPaidSickLeaveMail;

    @Autowired
    private ParentalLeaveMail parentalLeaveMail;

    @Autowired
    private UnpaidVacationMail unpaidVacationMail;

    @Autowired
    private VacationMail vacationMail;

    @Autowired
    private WfhMail wfhMail;

    public Optional<Mail> create(Type timeOffRequestType) {
        switch (timeOffRequestType) {
            case BEREAVEMENT_LEAVE:
                return Optional.of(bereavementLeaveMail);
            case BLOOD_DONATION:
                return Optional.of(bloodDonationMail);
            case COMPENSATORY_LEAVE:
                return Optional.of(compensatoryLeaveMail);
            case FULLY_PAID_SICK_LEAVE:
                return Optional.of(fullyPaidSickLeaveMail);
            case PARENTAL_LEAVE:
                return Optional.of(parentalLeaveMail);
            case UNPAID_VACATION:
                return Optional.of(unpaidVacationMail);
            case VACATION:
                return Optional.of(vacationMail);
            case WFH:
                return Optional.of(wfhMail);
            default:
                return Optional.empty();
        }
    }
}
