const profileAddAnchor = window.document.getElementById('profileAddAnchor');
const profileAddDialog = window.document.getElementById('profileDialog');
const profileAddForm = window.document.getElementById('profileAddForm');
const profileEditForm = window.document.getElementById('profileEditForm');

const functions = {
    handleEditStart: (params) => {
        // alert('편집 시작!');
        profileAddDialog.classList.add('edit-mode');
    },
    handleEditComplete: (params) => {
        profileAddDialog.classList.remove('edit-mode');
    },
    handleEditEachStart: (params) => {
        // 관리모드일때 프로필 클릭시 이동 하면안되게 한것....관리 모드가 아닐경우 이동한다.
        if (profileAddDialog.classList.contains('edit-mode')) {
            params.event.preventDefault();
            const name = params.element.dataset.name;
            const kids = params.element.dataset.kids === 'true';
            const profileIndex = params.element.dataset.profileIndex;
            profileAddDialog.classList.remove('visible');
            profileEditForm.classList.add('visible');
            profileEditForm['oldName'].value = name;
            profileEditForm['name'].value = name;
            profileEditForm['isKids'].checked = kids;
            profileEditForm['profileIndex'].value = profileIndex;
            profileEditForm.querySelector('.profile-image').setAttribute('src', `/user/resources/images/profile${profileIndex}.png`);

            profileEditForm['name'].focus();

        }
    },
    handleEditEachCancel: (params) => {
        profileAddDialog.classList.add('visible');
        profileEditForm.classList.remove('visible');
    }
};

window.document.body.querySelectorAll('[data-func]').forEach(x => {
    x.addEventListener('click', e => {
        const dataFuncValue = x.dataset.func;
        if (typeof (dataFuncValue) === 'string' && typeof (functions[dataFuncValue]) == 'function') {
            functions[dataFuncValue]({
                element: x,
                event: e
            });
        }
    });
});


profileAddAnchor.addEventListener('click', e => {
    e.preventDefault();
    profileAddDialog.classList.remove('visible');
    profileAddForm.classList.add('visible');
    profileAddForm['isKids'].checked = false;
    profileAddForm['name'].value = '';
    profileAddForm['name'].focus();
});

profileAddForm['cancel'].addEventListener('click', () => {
    profileAddDialog.classList.add('visible');
    profileAddForm.classList.remove('visible');
});

profileAddForm.onsubmit = e => {
    e.preventDefault();
    if (profileAddForm['name'].value === '') {
        profileAddForm['name'].focus();
        return false;
    }
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'add');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseJson = JSON.parse(xhr.responseText);
                const result = responseJson['result'];
                switch (result) {
                    case 'failure_duplicate_name':
                        alert(`해당 이름(${profileAddForm['name'].value})은 이미 사용 중입니다.`);
                        break;
                    case 'success':
                        window.location.href = 'choose';
                        break;
                    default:
                        alert('알 수 없는 이유로 프로필을 추가하지 못했습니다.\n\n잠시 후 다시 시도해주시거나 고객센터를 통해 문의해주세요.');
                }
            } else {
                alert('서버와 통신하지 못하였습니다.\n\n잠시 후 다시 시도해주시거나 고객센터를 통해 문의해주세요.');
            }
        }
    };
    const data = new FormData();
    data.append('name', profileAddForm['name'].value);
    data.append('kids', profileAddForm['isKids'].checked);
    xhr.send(data);
};

profileEditForm.onsubmit = e => {
    e.preventDefault();
    if (profileEditForm['name'].value === '') {
        profileEditForm['name'].focus();
        return false;
    }


    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'modify');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                // 성공 시
                // alert('성공')
                const responseJson = JSON.parse(xhr.responseText);
                switch (responseJson['result']) {
                    case 'failure_duplicate_name':
                        alert('해당이름은 이미 사용중 입니다.');
                        break;
                    case 'success':
                        window.location.href = 'choose';
                        break;
                    default:
                        alert('알 수 없는 이유로 프로필을 추가하지 못하였습니다.\n\n잠시 후 다시 이용 부탁드립니다.');
                }
            } else {
                // 실패시
                alert('서버와 통신하지 못하였습니다.\n\n잠시후 다시 시도해주세요.')
            }
        }
    };
    const data = new FormData();
    data.append('name', profileEditForm['name'].value);
    data.append('kids', profileEditForm['isKids'].checked);
    data.append('profileIndex', profileEditForm['profileIndex'].value);
    data.append('oldName', profileEditForm['oldName'].value);
    xhr.send(data);
};



















