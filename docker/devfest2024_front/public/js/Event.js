export function dialogEvent() {
    const openDialogButton = document.getElementById('addButton');
    const modal = document.getElementById('myModal');
    const modalContent = document.getElementsByClassName('modal-content')[0];

    openDialogButton.addEventListener('click', function() {
        modal.style.display = 'block';
    });

    modal.addEventListener('click', function(event) {
        if (event.target === modalContent) { // Modification ici
            modal.style.display = 'none';
        }
    });

    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
}

export function phoneEvent() {
    const phoneInput = document.getElementById('phone');

    phoneInput.addEventListener('input', function(event) {
        const phoneNumber = event.target.value.replace(/\D/g, '');
        const formattedPhoneNumber = formatPhoneNumber(phoneNumber);
        event.target.value = formattedPhoneNumber;
    });
}

function formatPhoneNumber(phoneNumber) {
    let formatted = '';
    for (let i = 0; i < phoneNumber.length; i++) {
        if (i > 0 && i % 2 === 0) {
            formatted += ' ';
        }
        formatted += phoneNumber[i];
    }
    return formatted.trim();
}

export function scoreEvent() {
    const scoreInput = document.getElementById('score');
    scoreInput.addEventListener('input', function(event) {
        const score = event.target.value.replace(/\D/g, '');
        event.target.value = formatNumber(score)
    });
}

function formatNumber(value) {
    if (value){
        return parseInt(value).toLocaleString()
    }
    return value
}

export function filterEvent(func){
    if(window.location.pathname !== '/index.html' && window.location.pathname !== '/'){
        const selectFilter = document.getElementById("table-select-filter")
        selectFilter.addEventListener('change', function (event){
            func(event.target.value)
        })
    }
}

export function nextPageButton(func) {
    if(window.location.pathname !== '/index.html'  && window.location.pathname !== '/'){
        const nextPageButton = document.getElementById("next-page-button")
        nextPageButton.addEventListener('click', function () {func()} )
    }
}
export function previousPageButton(func) {
    if(window.location.pathname !== '/index.html'  && window.location.pathname !== '/'){
        const nextPageButton = document.getElementById("previous-page-button")
        nextPageButton.addEventListener('click', function () {func()} )
    }
}

export function firstPageButton(func) {
    if(window.location.pathname !== '/index.html'  && window.location.pathname !== '/'){
        const nextPageButton = document.getElementById("first-page-button")
        nextPageButton.addEventListener('click', function () {func()} )
    }
}

export function lastPageButton(func) {
    if(window.location.pathname !== '/index.html'  && window.location.pathname !== '/'){
        const nextPageButton = document.getElementById("last-page-button")
        nextPageButton.addEventListener('click', function () {func()} )
    }
}

export function dialogResetEvent() {
    document.getElementById("resetButton").addEventListener('click',function (){
        const popup = document.getElementById('custom-reset-popup');
        popup.style.display = 'block';


        document.getElementById('confirm-reset-true')
            .addEventListener('click',function (){
                fetch(`${BASE_URL}:${PORT_BACK}/users/delete`, {
                    method: 'DELETE',
                }).then( () => {
                    window.location.reload()
                });
                popup.style.display = 'none';

            })

        document.getElementById('confirm-reset-false').addEventListener('click',function (){
            popup.style.display = 'none';
        });
    })
}

export function dialogWinnerEvent() {
    document.getElementById("winnerButton").addEventListener('click',async function () {
        const popup = document.getElementById('custom-winner-popup');

        popup.style.display = 'block';

        const responce = await fetch(`${BASE_URL}:${PORT_BACK}/users/winner`)
        const data = await responce.json()

        console.log(data)
        document.getElementById("winner-firstname").innerText = data.firstname
        document.getElementById("winner-lastname").innerText = data.lastname
        document.getElementById("winner-phone").innerText = data.phone
        document.getElementById("winner-email").innerText = data.email
        document.getElementById("winner-house").innerText = data.house
        document.getElementById("winner-score").innerText = data.score


        document.getElementById('confirm-winner-true').addEventListener('click', function () {
            popup.style.display = 'none';
        })
    })
}

export function imgRedirection() {
    const targetUrl = new URL(`${BASE_URL}:${PORT_FRONT}/page/PageScore.html`);
    document.getElementById("front-img").addEventListener("click", function (){
        targetUrl.searchParams.set('house', "FRONT");
        window.location.href = targetUrl.toString();
    })
    document.getElementById("back-img").addEventListener("click", function (){
        targetUrl.searchParams.set('house', "BACK");
        window.location.href = targetUrl.toString();
    })
    document.getElementById("cicd-img").addEventListener("click", function (){
        targetUrl.searchParams.set('house', "CICD");
        window.location.href = targetUrl.toString();
    })
    document.getElementById("mobile-img").addEventListener("click", function (){
        console.log("test")
        targetUrl.searchParams.set('house', "MOBILE");
        window.location.href = targetUrl.toString();
    })
}
