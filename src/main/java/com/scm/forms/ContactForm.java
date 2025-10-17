package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import com.scm.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {
    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Invalid Email Address")
    @NotBlank(message = "email is required")
    private String email;

    private String password;
    @NotBlank(message = "Address is required")

    private String address;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "invalid phone number")
    private String phone;

    private boolean favorite;

    private String description;

    private String websiteLink;

    private String linkedInLink;

    // annotation create karenge jo file validate karega
    // size
    // resolution

    @ValidFile(message = "Invalid File")
    private MultipartFile contactImage;
}
