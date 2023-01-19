const registerForm = window.document.getElementById('register-form');
registerForm.focusAndSelect = (name) => {
    registerForm[name].focus();
    registerForm[name].select();
};
HTMLInputElement.prototype.focusAndSelect = function () {
  this.focus();
  this.select();
};

registerForm.onsubmit = e => {
    const warning = registerForm.querySelector('[rel=warning]');
    warning.show = (message) => {
      warning.innerText = message;
      warning.classList.add('visible');
    };
    warning.innerText = '';
    warning.classList.remove('visible');
    if (registerForm['email'].value === '') {
        warning.show('이메일을 입력해주세요.');
        registerForm['email'].focusAndSelect();
        e.preventDefault();
        return false;
    }
    if (registerForm['password'].value === '') {
        warning.show('비밀번호를 입력해주세요.');
        registerForm.focusAndSelect('password');
        e.preventDefault();
        return false;
    }
    if (registerForm['passwordCheck'].value === '') {
        warning.show('비밀번호 재입력을 제대로 입력해주세요.');
        registerForm.focusAndSelect('passwordCheck');
        e.preventDefault();
        return false;
    }
    if (registerForm['password'].value !== registerForm['passwordCheck'].value) {
      warning.show('입력한 비밀번호가 서로 다릅니다. 다시 확인해 주세요.');
      registerForm.focusAndSelect('passwordCheck');
      e.preventDefault();
      return false;
  }
    if (registerForm['name'].value === '') {
        warning.show('소유자 이름을 입력해주세요.');
        registerForm.focusAndSelect('name');
        e.preventDefault();
        return false;
    }
    if (!registerForm['agreeTerm'].checked) {
        warning.show('개인정보 처리방침을 읽고 동의하지 않으면 회원가입을 계속할 수 없습니다.');
        registerForm['agreeTerm'].focus();
        e.preventDefault();
        return false;
    }
    if (!registerForm['agreeEmail'].checked && confirm('매년 매월 매일 매시 매분 매초 혜택이 가득한 쓸 떄 없는 이메일을 보내드립니다.\n\n다시 한번 검토해 보시겠습니까?')) {
        registerForm['agreeEmail'].focus();
        e.preventDefault();
        return false;
    }
};