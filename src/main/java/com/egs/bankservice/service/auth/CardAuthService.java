package com.egs.bankservice.service.auth;

import com.egs.bankservice.controller.model.auth.CardAuthRequest;
import com.egs.bankservice.controller.model.auth.CardAuthResponse;

public interface CardAuthService {

    CardAuthResponse cardAuth(CardAuthRequest cardAuthRequest);
}
