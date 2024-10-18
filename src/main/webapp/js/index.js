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


class XValidator extends Validator {
    validate(value) {
        if (isNaN(value)) {
            throw new InvalidValueException("Неверное значение X");
        }

        const decimalPart = String(value).trim().split('.')[1];
        if (decimalPart && decimalPart.length > 15) {
            throw new InvalidValueException("Слишком много знаков после запятой");
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

        const y = Number(value);
        if (y < -4 || y > 4) {
            throw new InvalidValueException("Число Y не входит в диапазон");
        }
        return true;
    }
}

class RValidator extends Validator {
    validate(value) {
        if (!value) {
            throw new InvalidValueException("Пожалуйста, выберите значение R");
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

// Функция для отправки данных с гибкостью метода
async function submitForm(values, method = 'GET') {
    const params = new URLSearchParams(values).toString();
    const response = await fetch(`/lab2-1.0-SNAPSHOT/controller-servlet?${params}`, {
        method: method,
        headers: {'Content-Type': 'application/json;charset=utf-8'}
    });

    if (response.ok) {
        const html = await response.text();
        document.open();
        document.write(html);
        document.close();
    } else {
        throw new Error("Ошибка при получении данных с сервера.");
    }
}

// Обработчик кликов по SVG
function handleClick(event) {
    const svg = document.getElementById("plate");
    const point = svg.createSVGPoint();
    point.x = event.clientX;
    point.y = event.clientY;
    const coords = point.matrixTransform(svg.getScreenCTM().inverse());

    const x = (coords.x - 250) / 33;
    let y = (250 - coords.y) / 25;
    y = Math.round(y);

    const values = {
        x: x.toFixed(2),
        y: y,
        r: document.querySelector('input[name="r"]:checked')?.value
    };

    try {
        validateFormInput(values);
        submitForm(values).catch(error => alert(error.message));
    } catch (e) {
        alert(e.message);
    }
}

// Универсальная обработка формы
async function handleSubmitForm(ev, form) {
    ev.preventDefault();
    const formData = new FormData(form);
    const values = {
        x: formData.get('x'),
        y: formData.get('y'),
        r: formData.get('r')
    };

    const errorDiv = document.getElementById("error");

    try {
        validateFormInput(values);
        errorDiv.hidden = true;
        await submitForm(values);
    } catch (e) {
        errorDiv.hidden = false;
        errorDiv.textContent = e.message;
    }
}

document.getElementById("data-form").addEventListener('submit', (ev) => handleSubmitForm(ev, ev.target));

// Добавляем обработчик клика на SVG
document.getElementById("plate").addEventListener("click", handleClick);

// Автозапуск аудио при первом клике
document.addEventListener('DOMContentLoaded', () => {
    document.body.addEventListener('click', () => {
        document.getElementById("intro_audio").play();
    }, {once: true});
});
