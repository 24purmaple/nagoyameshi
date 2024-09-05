package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Company;
import com.example.nagoyameshi.form.CompanyEditForm;
import com.example.nagoyameshi.repository.CompanyRepository;

@Service
public class CompanyService {
	private final CompanyRepository companyRepository;
	
	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}
	
	@Transactional
	public void update(CompanyEditForm companyEditForm) {
		Company company = companyRepository.getReferenceById(companyEditForm.getCompanyId());
		
		company.setCompanyName(companyEditForm.getCompanyName());
		company.setPostalCode(companyEditForm.getPostalCode());
		company.setAddress(companyEditForm.getAddress());
		company.setManagingDirector(companyEditForm.getManagingDirector());
		company.setEstablished(companyEditForm.getEstablished());
		company.setCapital(companyEditForm.getCapital());
		company.setService(companyEditForm.getService());
		company.setEmployees(companyEditForm.getEmployees());
		company.setPhoneNumber(companyEditForm.getPhoneNumber());
		company.setEmail(companyEditForm.getEmail());
		
		companyRepository.save(company);
		
	}
}