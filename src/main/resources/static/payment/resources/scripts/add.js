
const addForm = window.document.getElementById('add-form');
const addFormCycles = Array.from(addForm.querySelectorAll('[data-cycle]'));

const functions = {
    sendContactVerificationCode: (params) => {
        if (addForm['contactTelecom'].value === '-1') {
            alert("통신사를 선택해주세요.");
            addForm['contactTelecom'].focus();
            return;
        }
        if (addForm['contactFirst'].value === '') {
            alert('연락처를 입력해주세요.');
            addForm['contactFirst'].focus();
            return;
        }
        if (addForm['contactSecond'].value === '') {
            alert('연락처를 입력해주세요.');
            addForm['contactSecond'].focus();
            return;
        }
        if (addForm['contactThird'].value === '') {
            alert('연락처를 입력해주세요.');
            addForm['contactThird'].focus();
            return;
        }
        const xhr = new XMLHttpRequest();
        xhr.open('GET', `./contact-verification-code?contactFirst=${addForm['contactFirst'].value}&contactSecond=${addForm['contactSecond'].value}&contactThird=${addForm['contactThird'].value}`);
        xhr.onreadystatechange = () => {
            if(xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status >= 200 && xhr.status < 300) {
                    const responseJson = JSON.parse(xhr.responseText);
                    if (responseJson['result'] === 'success') {
                        addForm['contactVerificationCode'].removeAttribute('disabled');

                        addForm['contactTelecom'].setAttribute('disabled', 'disabled');
                        addForm['contactFirst'].setAttribute('disabled', 'disabled');
                        addForm['contactSecond'].setAttribute('disabled', 'disabled');
                        addForm['contactThird'].setAttribute('disabled', 'disabled');
                        addForm['contactVerificationSalt'].value = responseJson['salt'];
                        addForm['contact-verification-send'].classList.remove('visible');
                        addForm['contact-verification-check'].classList.add('visible');
                        addForm['contactVerificationCode'].focus();
                        alert('인증 번호가 포함된 문자를 전송하였습니다.\n5분 이내에 입력해주세요.');
                    } else {
                        alert('인증 문자를 전송하지 못하였습니다.\n잠시 후 다시 시도해 주세요.')
                    }
                } else {
                    alert('서버와 통신하지 못하였습니다.\n잠시 후 다시 시도해 주세요.');
                }
            }
        };
        xhr.send();
    },
    checkContactVerificationCode: (params) => {
        if (addForm['contactVerificationCode'].value.length < 6) {
            alert('인증 번호를 입력해 주세요.');
            addForm['contactVerificationCode'].focus();
            return;
        }
        const xhr = new XMLHttpRequest();
        const formData = new FormData();
        formData.append('contactFirst', addForm['contactFirst'].value);
        formData.append('contactSecond', addForm['contactSecond'].value);
        formData.append('contactThird', addForm['contactThird'].value);
        formData.append('salt', addForm['contactVerificationSalt'].value);
        formData.append('code', addForm['contactVerificationCode'].value);
        xhr.open('POST', `./contact-verification-code`);
        xhr.onreadystatechange = () => {
            if(xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status >= 200 && xhr.status < 300) {
                    const responseJson = JSON.parse(xhr.responseText);
                    switch (responseJson['result']) {
                        case 'success' :
                            alert('연락처가 성공적으로 인증되었습니다.');
                            addForm['contactVerificationCode'].setAttribute('disabled', 'disabled')
                            addForm['contact-verification-check'].classList.remove('visible');
                            break;
                        case 'failure_expired':
                            alert('인증 번호가 만료되었습니다.\n다시 시도해 주세요.');
                            break;
                        default:
                            alert('인증 번호가 올바르지 않습니다.\n다시 시도해 주세요.');
                            addForm['contactVerificationCode'].focus();
                            addForm['contactVerificationCode'].select();
                    }
                } else {
                    alert('서버와 통신하지 못하였습니다.\n잠시 후 다시 시도해 주세요.');
                }
            }
        };
        xhr.send(formData);
    },
    closeAddressSearch: () => {
        window.document.body.classList.remove('searching');
    },
    openAddressSearch: (params) => {
        const addressSearchContainer = window.document.getElementById('addressSearchContainer');
        const dialog = addressSearchContainer.querySelector(':scope > .dialog');
        dialog.innerHTML = '';
        new daum.Postcode({
            oncomplete: (data) => {
                addForm['addressPostal'].value = data.zonecode;
                addForm['addressPrimary'].value = data.address;
                addForm['addressSecondary'].focus();
                addForm['addressSecondary'].select();
                window.document.body.classList.remove('searching');
            }
        }).embed(dialog);
        window.document.body.classList.add('searching');
    },
};

window.document.body.querySelectorAll('[data-func]').forEach(element => {
    element.addEventListener('click', event => {
        const dataFunc = element.dataset.func ?? '';
        if (typeof(functions[dataFunc]) === 'function'){
            functions[dataFunc]({
                element: element,
                event: event
            });
        }
    });
});

for (let i = 0; i < addFormCycles.length; i++) {
    addFormCycles[i].addEventListener('input', e => {
        const maxLength = parseInt(e.target.getAttribute('maxlength'));
        if (e.target.value.length === maxLength) {
            addFormCycles[i + 1]?.focus();
            addFormCycles[i + 1]?.select();
        }
    });
};

addForm.querySelectorAll('[data-format="number"]').forEach(x => {
    x.addEventListener('input', e => e.target.value = e.target.value.replace(/[^0-9]/g, ''));
    x.addEventListener('keypress', e => (e.keyCode < 48 || e.keyCode > 57 ? e : null)?.preventDefault());
});

addForm['cardNumberFirst'].addEventListener('input', e => {
    const number = e.target.value;
    let cvcMaxLength = 3;
    let iin = '';
    if (['34', '37'].indexOf(number.substring(0, 2)) > -1) {
        cvcMaxLength = 4;
        iin = 'amex';
    } else if (number.substring(0, 1) === '4') {
        iin = 'visa';
    } else if (['51', '52', '53', '54', '55'].indexOf(number.substring(0, 2)) > -1) {
        iin = 'master';
    } else if (['622', '624', '628'].indexOf(number.substring(0, 3)) > -1) {
        iin = 'unionpay';
    } else if (['300', '305'].indexOf(number.substring(0, 3)) > -1 || ['36', '38', '39'].indexOf(number.substring(0, 2)) > -1 || number.substring(0, 4) === '3095') {
        iin = 'dinersclub';
    } else if (['3528', '3589'].indexOf(number.substring(0, 4)) > -1) {
        iin = 'jcb';
    } else if (number.substring(0, 1) === '9') {
        iin = 'bc';
    }
    addForm['cardNumberFirst'].dataset.card = iin;
    addForm['cardNumberCvc'].setAttribute('maxlength', cvcMaxLength + '');
});

addForm['cardNumberExpM'].addEventListener('focusout', e => {
    let value = parseInt(e.target.value);
    if (isNaN(value)) {
        value = ''
    } else {
        if (value < 1) {
            value = 1;
        } else if (value > 12) {
            value = 12;
        }
        if (value < 10) {
            value = '0' + value;
        }
    }
    e.target.value = value;
});

addForm['cardNumberExpY'].addEventListener('focusout', e => {
    let value = parseInt(e.target.value);
    if (isNaN(value)) {
        value = '';
    } else {
        if (value < 10) {
            value = '0' + value;
        }
    }
    e.target.value = value;
});

addForm.onsubmit = e => {
    for (let cardNumber of ['cardNumberFirst', 'cardNumberSecond' ,'cardNumberThird' , 'cardNumberFourth']) {
        if ( addForm[cardNumber].value === '' ) {
            alert('카드 번호를 입력해주세요');
            addForm[cardNumber].focus();
            addForm[cardNumber].select();
            e.preventDefault();
            return false;
        }
    }
    if (addForm['cardNumberExpM'].value === '') {
        alert('카드 유효기간을 입력해주세요.');
        addForm['cardNumberExpM'].focus();
        addForm['cardNumberExpM'].select();
        e.preventDefault();
        return false;
    }
    if (addForm['cardNumberExpY'].value === '') {
        alert('카드 유효기간을 입력해주세요.');
        addForm['cardNumberExpY'].focus();
        addForm['cardNumberExpY'].select();
        e.preventDefault();
        return false;
    }
    if (addForm['cardNumberCvc'].value === '') {
        alert('카드 CVC 번호를 입력해주세요.');
        addForm['cardNumberCvc'].focus();
        addForm['cardNumberCvc'].select();
        e.preventDefault();
        return false;
    }
    if (addForm['password'].value === '') {
        alert('카드 비밀번호를 입력해주세요.');
        addForm['password'].focus();
        addForm['password'].select();
        e.preventDefault();
        return false;
    }
    if (addForm['name'].value === '') {
        alert('카드 소유자 이름을 입력해 주세요.');
        addForm['name'].focus();
        addForm['name'].select();
        e.preventDefault();
        return false;
    }
    if (addForm['birthDp'].value === '') {
        alert('카드 소유자 생년월일을 입력해주세요.');
        addForm['birthDp'].focus();
        addForm['birthDp'].select();
        e.preventDefault();
        return false;
    }
    if (addForm['gender'].value === '') {
        alert('카드 소유자 주민번호 뒤 한자리를 입력해주세요.');
        addForm['gender'].focus();
        addForm['gender'].select();
        e.preventDefault();
        return false;
    }
    if (addForm['contact-verification-send'].classList.contains('visible') || addForm['contact-verification-check'].classList.contains('visible')) {
        alert('휴대폰 인증을 완료해 주세요.');
        e.preventDefault();
        return false;
    }
    if (addForm['addressPostal'].value === '' || addForm['addressPrimary'].value === '') {
        alert('주소 찾기 기능을 이용하여 주소를 선택해 주세요.');
        e.preventDefault();
        return false;
    }
    addForm['contactTelecom'].removeAttribute('disabled');
    addForm['contactFirst'].removeAttribute('disabled');
    addForm['contactSecond'].removeAttribute('disabled');
    addForm['contactThird'].removeAttribute('disabled');
    addForm['birth'].value = (addForm['gender'].value ==='1' || addForm['gender'].value === '2' ? '19' : '20') + addForm['birthDp'].value;
    addForm['code'].value = addForm['contactVerificationCode'].value;
    addForm['salt'].value = addForm['contactVerificationSalt'].value;
};


// const addFormCardNumberInputs = addForm.querySelectorAll('[rel="cardNumber"]');
// for (let i = 0; i < addFormCardNumberInputs.length; i++) {

//     addFormCardNumberInputs[i].addEventListener('input', e => {
//         e.target.value = e.target.value.replace(/[^0-9]/g, '');
//         if (e.currentTarget.value.length === 4) {
//             const nextInput = addFormCardNumberInputs[i + 1] ?? addForm['cardNumberExpM'];
//             nextInput.focus();
//             nextInput.select();
//         }
//     });
//     addFormCardNumberInputs[i].addEventListener('keypress', e => {
//         if (e.keyCode < 48 || e.keyCode > 57) {
//             e.preventDefault();
//         }
//     });
// }


























