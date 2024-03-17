function checkUnique(){
    let userId = $("#id").val();
    let csrfValue = $("input[name='_csrf']").val();
    let isValid = true;
    let form=document.getElementById("#registration_form");
    let email = $("#email").val();
    let emailParams = {id: userId, email: email, _csrf: csrfValue};
    const emailModal = new bootstrap.Modal(document.getElementById('email_modal'), {});
    $.post("[[@{/account/check_email}]]", emailParams, function (response){
        if(response =="Duplicated"){
            emailModal.show();
            isValid = false;
        }
    }).fail(function () {
        alert("Cannot connect to server");
    });
    let phone_number = $("#phone").val();
    let phoneParams = {id: userId, phone_number: phone_number, _csrf: csrfValue};
    const phoneModal = new bootstrap.Modal(document.getElementById('phone_modal'), {});
    $.post("[[@{/account/check_phone_number}]]", phoneParams, function (response){
        if(response =="OK"){
            form.submit();
        }else if(response =="Duplicated"){
            phoneModal.show();
            isValid = false;
        }
    }).fail(function () {
        alert("Cannot connect to server");
    });

}
function checkPassword(confirmPassword){
    if(confirmPassword.value!= $("#password").val()){
        confirmPassword.setCustomValidity("Пароли разные!");
    }else{
        confirmPassword.setCustomValidity("");
    }
}