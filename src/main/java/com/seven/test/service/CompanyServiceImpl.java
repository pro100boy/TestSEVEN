package com.seven.test.service;

import com.seven.test.AuthorizedUser;
import com.seven.test.model.Company;
import com.seven.test.model.User;
import com.seven.test.repository.CompanyRepository;
import com.seven.test.util.UserUtil;
import com.seven.test.util.exception.NotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.seven.test.AuthorizedUser.userHasAuthority;
import static com.seven.test.util.ValidationUtil.*;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public Company save(@NonNull Company company) {
        checkNew(company);
        Company c = repository.save(company);

        // create new company owner
        User newOwner = UserUtil.createNewForMail(c);
        userService.save(newOwner);

        // send email to just created owner
        try {
            String msg = String.format("Your login: %s%nYour password: %s%nYour company: %s", newOwner.getEmail(), "admin", company.getName());
            emailService.sendSimpleMessage(newOwner.getEmail(), "New company owner", msg);
        } catch (MailSendException ex)
        {
            // catch the exception here because Company and User have to be created anyway
            // TODO логировать
            ex.printStackTrace();
        }

        return c;
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Override
    public Company get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findOne(id), id);
    }

    @Override
    public List<Company> getAll() {
        if (userHasAuthority("ADMIN"))
            return repository.findAll(new Sort(Sort.Direction.ASC, "name"));
        else {
            return Arrays.asList(get(AuthorizedUser.companyId()));
        }
    }

    @Override
    public void update(@NonNull Company company, int id) {
        checkIdConsistent(company, id);
        repository.save(company);
    }
}
