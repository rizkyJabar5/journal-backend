package com.journal.florist.backend.feature.customer.service;

import com.journal.florist.app.common.messages.BaseResponse;
import com.journal.florist.app.common.messages.SuccessResponse;
import com.journal.florist.app.security.SecurityUtils;
import com.journal.florist.backend.exceptions.AppBaseException;
import com.journal.florist.backend.exceptions.IllegalException;
import com.journal.florist.backend.exceptions.NotFoundException;
import com.journal.florist.backend.feature.customer.dto.CustomerMapper;
import com.journal.florist.backend.feature.customer.dto.CustomerRequest;
import com.journal.florist.backend.feature.customer.dto.UpdateCustomerRequest;
import com.journal.florist.backend.feature.customer.model.Company;
import com.journal.florist.backend.feature.customer.model.CustomerDebt;
import com.journal.florist.backend.feature.customer.model.Customers;
import com.journal.florist.backend.feature.customer.repositories.CustomerRepository;
import com.journal.florist.backend.feature.utils.EntityUtil;
import com.journal.florist.backend.feature.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

import static com.journal.florist.app.constant.JournalConstants.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerDebtService customerDebtService;
    private final CustomerMapper customerMapper;
    private final Validator validator;

    @Override
    public Page<CustomerMapper> getCustomerByName(String name, Pageable pageable) {
        return repository.findByNameIgnoreCaseContaining(name, pageable)
                .map(customerMapper::mapToEntity);
    }

    @Override
    public Page<CustomerMapper> findAllCustomer(Pageable pageable) {
        return repository.findAllCustomers(pageable)
                .map(customerMapper::mapToEntity);

    }

    @Override
    public Customers getCustomerId(String customerId) {
        return repository.findByPublicKey(customerId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(NOT_FOUND_MSG, EntityUtil.getName(Customers.class), customerId)));
    }

    @Override
    public BaseResponse addCustomer(CustomerRequest customer) {
        Authentication authentication = SecurityUtils.getAuthentication();
        String createdBy = authentication.getName();

        Customers entity = new Customers();

        boolean persisted = repository.existsByPhoneNumber(customer.getCustomerPhone());
        if(persisted){
            throw new IllegalException(UNIQUE_PHONE_NUMBER);
        }

        entity.setName(customer.getCustomerName());
        boolean validPhoneNumber = validator.isValidPhoneNumber(customer.getCustomerPhone());
        if (!validPhoneNumber) {
            throw new AppBaseException(String.format(NOT_VALID_FIELD, "Phone number"));
        }
        entity.setPhoneNumber(customer.getCustomerPhone());

        if (customer.getCompanyName() != null) {
            Company company = new Company();
            company.setCompanyName(customer.getCompanyName());
            entity.setCompany(company);
        }

        if (customer.getAddress() != null) {
            Company company = new Company();
            company.setAddress(customer.getAddress());
            company.setCompanyName(entity.getCompany().getCompanyName());
            entity.setCompany(company);
        }
        entity.setCreatedBy(createdBy);
        entity.setCreatedAt(new Date(System.currentTimeMillis()));

        CustomerDebt debt = customerDebtService.addDebtCustomer(entity, BigDecimal.ZERO);
        entity.setCustomerDebt(debt);

        repository.save(entity);
        getLogger().info("{} is successfully add to store", entity.getPublicKey());

        CustomerMapper mapper = customerMapper.mapToEntity(entity);

        return new BaseResponse(
                HttpStatus.CREATED,
                String.format(SUCCESSFULLY_ADD_NEW_ENTITY, EntityUtil.getName(Customers.class).toLowerCase()),
                mapper);
    }

    @Override
    public BaseResponse updateCustomer(UpdateCustomerRequest customer) {
        Authentication authentication = SecurityUtils.getAuthentication();
        String updatedBy = authentication.getName();

        Customers entity = getCustomerId(customer.getCustomerId());

        if (customer.getCustomerName() != null) {
            entity.setName(customer.getCustomerName());
        }
        if (customer.getCustomerPhone() != null) {
            boolean validPhoneNumber = validator.isValidPhoneNumber(customer.getCustomerPhone());
            if (!validPhoneNumber) {
                throw new AppBaseException(String.format(NOT_VALID_FIELD, "Phone number"));
            }
            entity.setPhoneNumber(customer.getCustomerPhone());
        }

        if (customer.getCompanyName() != null) {
            Company company = entity.getCompany();
            company.setCompanyName(customer.getCompanyName());
            entity.setCompany(company);
        }
        if (customer.getAddress() != null) {
            Company company = entity.getCompany();
            company.setAddress(customer.getAddress());
            entity.setCompany(company);
        }

        entity.setLastModifiedBy(updatedBy);
        entity.setLastModifiedDate(new Date(System.currentTimeMillis()));

        repository.save(entity);
        getLogger().info("Customer with id {} is successfully updated", entity.getPublicKey());

        CustomerMapper mapper = customerMapper.mapToEntity(entity);
        return new BaseResponse(
                HttpStatus.CREATED,
                String.format("Customer with id %s is successfully updated", entity.getPublicKey()),
                mapper);
    }

    @Override
    public SuccessResponse deleteCustomer(String id) {
        Customers persisted = getCustomerId(id);
        repository.delete(persisted);

        getLogger().info("Successfully deleted category {}", persisted.getPublicKey());
        return new SuccessResponse(
                String.format(SUCCESSFULLY_DELETE_OPERATION, persisted.getPublicKey()),
                SuccessResponse.StatusOperation.SUCCESS);
    }

    @Override
    public long countAllCustomer() {
        return repository.count();
    }
}

