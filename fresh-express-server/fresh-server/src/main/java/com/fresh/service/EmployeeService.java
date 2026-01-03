package com.fresh.service;

import com.fresh.dto.EmployeeLoginDTO;
import com.fresh.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

}
