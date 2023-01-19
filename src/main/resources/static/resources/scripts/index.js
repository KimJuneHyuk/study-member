const Components = {
    MediaContainer: {
        getElement: () => window.document.getElementById('media-container'),
        //getElement 는 html id= media-container
        getActorsElement: () => Components.MediaContainer.getElement().querySelector('[rel=actors]'),
        //getActorElement 는 html 의 id=media-container > 아래에 있는 요소중 rel=actors
        getGenresElement: () => Components.MediaContainer.getElement().querySelector('[rel=genres]'),
        //getGenresElement 는 html 의 id=media-container >  아래에 있는 요소중 rel=genres 인것
        getFeaturesElement: () => Components.MediaContainer.getElement().querySelector('[rel=features]'),
        //getFeaturesElement 는 html 의 요소 id=media-container >  아래 요소중 rel=features
        show: () => Components.MediaContainer.getElement().classList.add('visible'),
        //media-container 의 classList 이름에 visible 추가 함으로써 화면을 보이가하는 기능
        hide: () => Components.MediaContainer.getElement().classList.remove('visible'),
        //media-container 의 classList 로 클래스 이름에 visible 지움으로써 화면에 보이기를 없앤다.
        showLoading : () => Components.MediaContainer.getElement().classList.add('loading'),
        //showLoading: media-container 의 클래스 이름으로 loading 을 추가하겠다.
        hideLoading : () => Components.MediaContainer.getElement().classList.remove('loading'),
        //hideLoading: media-container 의 클래스 이름으로 loading 을 제거하겠다.
        clearActors: () => {
            Components.MediaContainer.getActorsElement().innerHTML = '';
            // getActorsElement 의 내용을 제거하고 다시 mediaContainer 을 호출한다.
            return Components.MediaContainer;
        },
        clearFeatures: () => {
            Components.MediaContainer.getFeaturesElement().innerHTML = '';
            // getFeaturesElement 의 내용을 제거하고 다시 mediaContainer 을 호출한다.
            return Components.MediaContainer;
        },
        clearGenres: () => {
            Components.MediaContainer.getGenresElement().innerHTML = '';
            // getGenresElement 의 내용을 제거하고 다시 mediaContainer 을 호출한다.
            return Components.MediaContainer;
        },
        setLogoImageSrc: (src) => {
            Components.MediaContainer.getElement().querySelector('[rel=logoImage]').setAttribute('src', src);
            //media-container 아래의 rel=logoImage 인 요소의 값을 설정한다. 이름은 src, 값은 src(string)
            return Components.MediaContainer;
        },
        setTeaserVideoSrc: (src) => {
            Components.MediaContainer.getElement().querySelector('[rel=teaserVideo]').setAttribute('src', src);
            return Components.MediaContainer;
        },
        setPublishedYear: (year) => {
            Components.MediaContainer.getElement().querySelector('[rel=publishedYear]').innerText = year;
            return Components.MediaContainer;
        },
        setFhd: (b) => {
            Components.MediaContainer.getElement().querySelector('[rel=fhd]').style.display = b === true ? 'block' : 'none';
            return Components.MediaContainer;
        },
        setUhd: (b) => {
            Components.MediaContainer.getElement().querySelector('[rel=uhd]').style.display = b === true ? 'block' : 'none';
            return Components.MediaContainer;
        },
        setCommentary: (b) => {
            Components.MediaContainer.getElement().querySelector('[rel=commentary]').style.display = b === true ? 'block' : 'none';
            return Components.MediaContainer;
        },
        setDescription: (desc) => {
            Components.MediaContainer.getElement().querySelector('[rel=description]').innerText = desc;
            return Components.MediaContainer;
        },
        addActor: (actor) => {
            const actors = Components.MediaContainer.getActorsElement();
            const anchorElement = window.document.createElement('a');
            anchorElement.classList.add('link');
            anchorElement.setAttribute('href', '#');
            anchorElement.innerText = actor['text'];
            if (actors.children.length > 0) {
                actors.innerHTML += ', ';
            }
            actors.append(anchorElement);
        },
        addActors: (actors) => {
            actors.forEach(actor => Components.MediaContainer.addActor(actor));
            return Components.MediaContainer;
        },
        addGenre: (genre) => {
            const genres = Components.MediaContainer.getGenresElement();
            const anchorElement = window.document.createElement('a');
            anchorElement.classList.add('link');
            anchorElement.setAttribute('href', '#');
            anchorElement.innerText = genre['text'];
            if (genres.children.length > 0) {
                genres.innerHTML += ', ';
            }
            genres.append(anchorElement);
        },
        addGenres: (genres) => {
            genres.forEach(genre => Components.MediaContainer.addGenre(genre));
            return Components.MediaContainer;
        },
        addFeature: (feature) => {
            const features = Components.MediaContainer.getFeaturesElement();
            const anchorElement = window.document.createElement('a');
            anchorElement.classList.add('link');
            anchorElement.setAttribute('href', '#');
            anchorElement.innerText = feature['text'];
            if (features.children.length > 0) {
                features.innerHTML += ', ';
            }
            features.append(anchorElement);
        },
        addFeatures: (features) => {
            features.forEach(feature => Components.MediaContainer.addFeature(feature));
            return Components.MediaContainer;
        }
    }
};

const Functions = {
    Media: {
        hideDetail: () => {
            Components.MediaContainer.hide();
            window.history.pushState(null, null, '/');
        }
    },
    route: (params) => {
        params ??= {};
        const dataRoute = params.element?.dataset.route;
        if (typeof (dataRoute) !== 'string' || typeof (Router[dataRoute]) !== 'function') {
            return;
        }
        const dataArgs = JSON.parse(params.element?.dataset.args ?? '{}');
        let urlToPush = `/?action=${dataRoute}`;
        Object.keys(dataArgs).forEach(key => {
            urlToPush += `&${key}=${dataArgs[key]}`;
        });
        if (dataRoute === 'media') {
            params.args = {
                mediaIndex: dataArgs.mid
            };
        }
        window.history.pushState(null, null, urlToPush);
        Router[dataRoute](params);
    }
};

const Router = {
    route: () => {
        Router.resetPage();
        const url = new URL(window.location.href);
        const action = url.searchParams.get('action');
        if (typeof (Router[action]) !== 'function') {
            return;
        }
        const params = {};
        if (action === 'media') {
            params.args = {
                mediaIndex: url.searchParams.get('mid')
            };
        }
        Router[action](params);
    },
    resetPage: () => {
        Components.MediaContainer.hide();
    },
    media: (params) => {
        const xhr = new XMLHttpRequest();
        xhr.open('PATCH', `/media/info?mid=${params.args.mediaIndex}`);
        xhr.onreadystatechange = () => {
            if (xhr.readyState !== XMLHttpRequest.DONE) {
                return;
            }
            if (xhr.status < 200 || xhr.status >= 300) {
                alert('서버와 통신하지 못하였습니다.\n\n잠시 후 다시 시도해 주세요.');
                window.history.pushState(null, null, '/');
                Components.MediaContainer.hide();
                return;
            }
            const responseJson = JSON.parse(xhr.responseText);
            if (responseJson['result'] !== 'success') {
                alert('존재하지 않는 미디어이거나 정보를 불러올 수 없습니다.');
                window.history.pushState(null, null, '/');
                Components.MediaContainer.hide();
                return;
            }
            Components.MediaContainer.hideLoading();
            Components.MediaContainer
                .clearActors()
                .clearFeatures()
                .clearGenres();
            Components.MediaContainer
                .setTeaserVideoSrc(`/media/teaser-video?mid=${params.args.mediaIndex}`)
                .setLogoImageSrc(`/media/logo-image?mid=${params.args.mediaIndex}`)
                .setPublishedYear(new Date(responseJson['medium']['publishedYear']).getFullYear())
                .setFhd(responseJson['medium']['fhd'])
                .setUhd(responseJson['medium']['uhd'])
                .setCommentary(responseJson['medium']['commentary'])
                .setDescription(responseJson['medium']['description']);
            Components.MediaContainer
                .addActors(responseJson['actors'])
                .addFeatures(responseJson['features'])
                .addGenres(responseJson['genres']);
        };
        xhr.send();
        Components.MediaContainer
            .clearActors()
            .clearFeatures()
            .clearGenres()
            .setTeaserVideoSrc('')
            .setLogoImageSrc('')
            .setPublishedYear('')
            .setFhd(false)
            .setUhd(false)
            .setCommentary(false)
            .setDescription('');
        Components.MediaContainer.show();
        Components.MediaContainer.showLoading();
    }
};

window.addEventListener('popstate', () => {
    Router.route();
});

window.document.body.querySelectorAll('[data-func]').forEach(element => {
    element.addEventListener('click', event => {
        const dataFunc = element.dataset.func;
        if (typeof (dataFunc) !== 'string') {
            return;
        }
        const dataFuncArray = dataFunc.split('.');
        let lastObject = Functions;
        let toCall;
        for (let dataFuncItem of dataFuncArray) {
            if (typeof (lastObject[dataFuncItem]) === 'object') {
                lastObject = lastObject[dataFuncItem];
            } else if (typeof (lastObject[dataFuncItem]) === 'function') {
                toCall = lastObject[dataFuncItem];
                break;
            }
        }
        if (typeof (toCall) === 'function') {
            toCall({
                element: element,
                event: event
            });
        }
    });
});

Router.route();