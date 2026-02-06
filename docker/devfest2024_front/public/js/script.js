import {
    dialogEvent,
    phoneEvent,
    scoreEvent,
    filterEvent,
    nextPageButton,
    previousPageButton,
    lastPageButton,
    firstPageButton,
    dialogResetEvent,
    dialogWinnerEvent,
    imgRedirection
} from './Event.js';

let currentData = [];
let currentPage = 0;
let totalPage = 1;

document.addEventListener('DOMContentLoaded', async function() {

    dialogEvent();
    phoneEvent();
    scoreEvent();
    filterEvent(fetchFilteredData);
    nextPageButton(getNextPage);
    previousPageButton(getPreviousPage);
    lastPageButton(getLastPage);
    firstPageButton(getFirstPage);

    if (window.location.pathname !== '/index.html' && window.location.pathname !== '/') {
        await fetchFilteredData();
    } else {
        dialogResetEvent();
        dialogWinnerEvent();
        imgRedirection();
        await houseScore();
    }
    if (new URLSearchParams(window.location.search).size !== 0){
        await handleRedirectionAndScroll();
    }

    const message = document.getElementById('message');
    const form = document.getElementById('data-form');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = {
            phone: document.getElementById('phone').value,
            email: document.getElementById('email').value,
            firstname: document.getElementById('firstname').value,
            lastname: document.getElementById('lastname').value,
            house: document.getElementById('house').value,
            score: parseInt(document.getElementById('score').value.replace(/\s+/g, ''))
        };

        sendData(formData, form, message);
    });
});

async function fetchDataWithPage() {
    try {
        const response = await fetch(`${BASE_URL}:${PORT_BACK}/users?page=${currentPage}`);
        const data = await response.json();
        currentData = data.content;
        totalPage = data.totalPages;
        currentPage = data.pageable.pageNumber;
        fillDataTable();
        fillPagination();
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

async function fetchHouseData(house) {
    try {
        const response = await fetch(`${BASE_URL}:${PORT_BACK}/users/${house}?page=${currentPage}`);
        const data = await response.json();
        currentData = data.content;
        totalPage = data.totalPages === 0 ? 1 : data.totalPages;
        currentPage = data.pageable.pageNumber;
        fillDataTable();
        fillPagination();
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

async function fetchFilteredData(house = "", resetPagination = true) {
    if (resetPagination) {
        currentPage = 0;
    }
    if (house === "") {
        await fetchDataWithPage();
    } else {
        await fetchHouseData(house);
    }
}

async function sendData(data, form, message) {
    const modal = document.getElementById('myModal');
    try {
        const userExists = await checkUserHavePlay(data);
        if (userExists) {
            const response = await fetch(`${BASE_URL}:${PORT_BACK}/users`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });
            if (response.ok) {
                message.textContent = 'Envoyé avec succès !';
                message.style.color = 'green';
                currentPage = 0;
                await fetchDataWithPage();
                await houseScore();
                form.reset();
            } else {
                message.textContent = 'Une erreur est survenue !';
                message.style.color = 'red';
            }
            setTimeout(() => {
                message.textContent = '';
                modal.style.display = 'none';
            }, 500);
            const responseData = await response.json();
            goToRow(responseData.id, data.house);
        } else {
            form.reset();
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

function fillDataTable() {
    const dataTable = document.querySelector('#data-table tbody');
    if (!dataTable) {
        console.error('Error: dataTable is null. Ensure the element #data-table tbody exists.');
        return;
    }

    dataTable.innerHTML = '';

    currentData.forEach((item,index) => {
        const row = document.createElement('tr');
        row.id = item.id;
        row.innerHTML = `
            <th>${(currentPage * 20) + index + 1}</th>
            <td>${item.firstname}</td>
            <td>${item.lastname}</td>
            <td>${item.house.toLowerCase()}</td>
            <td>${item.score}</td>
        `;
        dataTable.appendChild(row);
    });
}

function checkUserHavePlay(data) {
    if (currentData.some(item => item.phone === data.phone)) {
        return showConfirmationPopup();
    }
    return Promise.resolve(true);
}

function showConfirmationPopup() {
    return new Promise((resolve) => {
        const popup = document.getElementById('custom-popup');
        const confirmTrue = document.getElementById('confirm-true');
        const confirmFalse = document.getElementById('confirm-false');

        popup.style.display = 'block';

        confirmTrue.onclick = () => {
            popup.style.display = 'none';
            resolve(true);
        };

        confirmFalse.onclick = () => {
            popup.style.display = 'none';
            resolve(false);
        };
    });
}

function fillPagination() {
    const pageNumber = document.getElementById("current-page");
    const pageTotal = document.getElementById("total-page");

    if (!pageNumber || !pageTotal) {
        console.error('Error: pageNumber or pageTotal is null. Ensure the elements #current-page and #total-page exist.');
        return;
    }

    pageNumber.innerText = currentPage + 1;
    pageTotal.innerText = totalPage;
}

async function houseScore() {
    if (window.location.pathname === '/index.html' || window.location.pathname === "/") {
        const frontEndScore = document.getElementById('front-score');
        const backEndScore = document.getElementById('back-score');
        const cicdScore = document.getElementById('cicd-score');
        const mobileScore = document.getElementById('mobile-score');
        try {
            const response = await fetch(`${BASE_URL}:${PORT_BACK}/users/score`);
            const data = await response.json();
            frontEndScore.innerText = data["FRONT"];
            backEndScore.innerText = data["BACK"];
            cicdScore.innerText = data["CICD"];
            mobileScore.innerText = data["MOBILE"];
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }
}

async function getNextPage() {
    if (currentPage < totalPage - 1) {
        currentPage++;
        const selectFilter = document.getElementById("table-select-filter");
        await fetchFilteredData(selectFilter.value, false);
    }
}

async function getPreviousPage() {
    if (currentPage > 0) {
        currentPage--;
        const selectFilter = document.getElementById("table-select-filter");
        await fetchFilteredData(selectFilter.value, false);
    }
}

async function getFirstPage() {
    currentPage = 0;
    const selectFilter = document.getElementById("table-select-filter");
    await fetchFilteredData(selectFilter.value, false);
}

async function getLastPage() {
    currentPage = totalPage - 1;
    const selectFilter = document.getElementById("table-select-filter");
    await fetchFilteredData(selectFilter.value, false);
}

async function goToRow(id, house = "") {
    const targetUrl = new URL(`${BASE_URL}:${PORT_FRONT}/page/PageScore.html`);
    targetUrl.searchParams.set('id', id);
    targetUrl.searchParams.set('house', house);

    window.location.href = targetUrl.toString();
}

async function handleRedirectionAndScroll() {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    const house = urlParams.get('house');

    if (house) {
        const filterElement = document.getElementById("table-select-filter");
        if (filterElement) {
            filterElement.value = house;
        }

        await fetchFilteredData(house, false);

        if(id){
            while (document.getElementById(id) === null) {
                await getNextPage();
            }

            const targetElement = document.getElementById(id);
            if (targetElement) {
                targetElement.scrollIntoView({ behavior: 'smooth' });
            }
        }
    }
}
