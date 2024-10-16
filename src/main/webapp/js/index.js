class InvalidValueException extends Error {
    constructor(message) {
        super(message);
        this.name = "InvalidValueException";
    }
}


class Validator {
    validate(value) {
        throw new Error("Метод validate() нужно переопределить");
    }
}


class RValidator extends Validator {
    validate(value) {
        if (value === null || value === undefined) {
            throw new InvalidValueException("Пожалуйста, выберите R");
        }
        return true;
    }
}






class XValidator extends Validator {
    validate(value) {
        if (isNaN(value)) {
            throw new InvalidValueException("Неверное значение X");
        }

        const yString = String(value).trim();
        const decimalPart = yString.split('.')[1];

        if (decimalPart && decimalPart.length > 15) {
            throw new InvalidValueException("Слишком большое количество знаков после запятой");
        }

        const x = Number(value);
        if (x < -3 || x > 3) {
            throw new InvalidValueException("Число X не входит в диапазон");
        }

        return true;
    }
}

class YValidator extends Validator {
    validate(value) {
        if (isNaN(value)) {
            throw new InvalidValueException("Неверное значение Y");
        }
        return true;
    }
}


const xValidator = new XValidator();
const yValidator = new YValidator();
const rValidator = new RValidator();


function validateFormInput(values) {


        xValidator.validate(values.x);
        yValidator.validate(values.y);
        rValidator.validate(values.r);
}


/** @type HTMLTableElement */
const table = document.getElementById("result-table");

/** @type HTMLDivElement */
const errorDiv = document.getElementById("error");

async function onSubmit(ev) {
    ev.preventDefault();

    const formData = new FormData(form);
    const values = {
        x: formData.get('x'),
        y: formData.get('y'),
        r: formData.get('r')
    };

    try {
        validateFormInput(values);
        errorDiv.hidden = true;
    } catch (e) {
        errorDiv.hidden = false;
        errorDiv.textContent = e.message;
        return
    }

    const newRow = table.insertRow(-1);
    const rowX = newRow.insertCell(0);
    const rowY = newRow.insertCell(1);
    const rowR = newRow.insertCell(2);
    const rowTime = newRow.insertCell(3);
    const rowNow = newRow.insertCell(4);
    const rowResult = newRow.insertCell(5);

    const y = parseFloat(String(values.y)).toFixed(2);

    rowX.textContent = String(values.x);
    rowY.textContent = y;
    rowR.textContent = String(values.r);

    try {
        const params = new URLSearchParams(values).toString()
        const response = await fetch(`/lab2-1.0-SNAPSHOT/controller-servlet?${params}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            }
        });


        if (response.ok) {

            const result = await response.json();
            rowTime.textContent = result.time;
            rowNow.textContent = result.now;
            const res = rowResult.textContent = result.result.toString();
            if (res === "true") {
                rowResult.style.color = "green"
                document.getElementById("true_audio").play();
            } else {
                document.getElementById("false_audio").play();
                rowResult.style.color = "orange"
            }


        } else {

            const result = await response.json();
            rowResult.style.color = "red";
            rowResult.textContent = "error";
            rowNow.textContent = result.now;
            console.error(result);
        }


        saveTableData();
    } catch (e) {
        rowResult.style.color = "red";
        rowResult.textContent = "connection error";
    }

}


const form = document.getElementById("data-form");
form.addEventListener('submit', onSubmit);
document.addEventListener('DOMContentLoaded', dataLoader);


function dataLoader() {

    const tableData = JSON.parse(sessionStorage.getItem('tableData')) || [];

    tableData.forEach(data => {
        const newRow = table.insertRow(-1);
        const rowX = newRow.insertCell(0);
        const rowY = newRow.insertCell(1);
        const rowR = newRow.insertCell(2);
        const rowTime = newRow.insertCell(3);
        const rowNow = newRow.insertCell(4);
        const rowResult = newRow.insertCell(5);

        rowX.textContent = data.x;
        rowY.textContent = data.y;
        rowR.textContent = data.r;
        rowTime.textContent = data.time;
        rowNow.textContent = data.now;
        rowResult.textContent = data.result;

        if (data.result === "true") {
            rowResult.style.color = "green";
        } else if (data.result === "false") {
            rowResult.style.color = "orange"
        } else {
            rowResult.style.color = "red"
        }
    });

}


function saveTableData() {
    const rows = Array.from(table.rows).slice(1);
    const tableData = rows.map(row => {
        return {
            x: row.cells[0].textContent,
            y: row.cells[1].textContent,
            r: row.cells[2].textContent,
            time: row.cells[3].textContent,
            now: row.cells[4].textContent,
            result: row.cells[5].textContent,
        };


    });

    sessionStorage.setItem('tableData', JSON.stringify(tableData));

}


document.addEventListener('DOMContentLoaded', () => {
    document.body.addEventListener('click', () => {
        document.getElementById("intro_audio").play();
    }, {once: true});
});

