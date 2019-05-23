package com.example.map.service;


import com.example.map.domain.Admin;
import com.example.map.model.ResultModel;

import java.util.Map;

public interface AdminService {

    ResultModel register(Admin admin);

    ResultModel login(Admin admin);

    String login(Admin admin, Map<String, String> errors);
}
