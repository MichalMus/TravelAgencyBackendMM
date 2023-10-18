package com.example.demo.service;

import com.example.demo.model.PersonsIdModel;
import com.example.demo.repository.PersonsIdRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class PersonsIdService {
    private final PersonsIdRepository personsIdRepository;

    public PersonsIdService(final PersonsIdRepository personsIdRepository){
        this.personsIdRepository=personsIdRepository;
    }

    public List<PersonsIdModel> getAllPersonsIdList(){
        return personsIdRepository.findAll();
    }
    public void addNewPersonsId(PersonsIdModel personsIdModel){
        personsIdRepository.save(personsIdModel);
    }
    public void deletePersonsIdById(Long id){
        personsIdRepository.deleteById(id);
    }
    public Optional<PersonsIdModel> findPersonById(Long id){
        return personsIdRepository.findById(id);
    }
    public List<PersonsIdModel> findPersonsBySurname(String surname){
        return personsIdRepository.findAllByPersonSurnameEqualsIgnoreCase(surname);
    }

public List<PersonsIdModel>findAllAndPage(int page, int size,Sort.Direction sort, String column ){
    return (List<PersonsIdModel>) personsIdRepository.findAllPersonsIdModel(
            PageRequest.of(page,size, Sort.by(sort,column)));
}

public List<PersonsIdModel>findAllBySurnameWithSort(String surname, int page, int size, Sort.Direction sort, String column){
        return (List<PersonsIdModel>) personsIdRepository.findAllPersonsBySurnameWithSortAndPaginate(surname,PageRequest.of(page,size, Sort.by(sort,column)));
}
public boolean isExist(Long id){
        return personsIdRepository.existsById(id);
}
}
