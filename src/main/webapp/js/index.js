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

        const decimalPart = String(value).trim().split('.')[1];
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

const table = document.getElementById("result-table");
const errorDiv = document.getElementById("error");
const form = document.getElementById("data-form");

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
        return;
    }

    const params = new URLSearchParams(values).toString();
    const response = await fetch(`/lab2-1.0-SNAPSHOT/controller-servlet?${params}`, {
        method: 'GET',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
    });

    if (response.ok) {

        // Получаем HTML-ответ
        const html = await response.text();
        // Заменяем содержимое текущей страницы на HTML-ответ
        document.open();
        document.write(html);
        document.close();

    } else {


        errorDiv.hidden = false;
        errorDiv.textContent = "Ошибка при получении данных.";
    }
}

form.addEventListener('submit', onSubmit);
document.addEventListener('DOMContentLoaded', () => {
    document.body.addEventListener('click', () => {
        document.getElementById("intro_audio").play();
    }, {once: true});
});
