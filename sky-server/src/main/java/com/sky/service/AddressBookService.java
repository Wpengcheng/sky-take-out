package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

/**
 * @Author 方唐镜
 * @Create 2024-01-01 16:59
 * @Description
 */
public interface AddressBookService {

	List<AddressBook> list(AddressBook addressBook);

	void save(AddressBook addressBook);

	AddressBook getById(Long id);

	void update(AddressBook addressBook);

	void setDefault(AddressBook addressBook);

	void deleteById(Long id);

}
