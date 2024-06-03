import React, { useState, useEffect } from 'react';
import axios from 'axios'; // Для отправки запросов на сервер

function DeliveryForm() {
    const [supplierId, setSupplierId] = useState('');
    const [deliveryDate, setDeliveryDate] = useState(new Date());
    const [items, setItems] = useState([]);
    const [suppliers, setSuppliers] = useState([]); // Массив поставщиков
    const [fruits, setFruits] = useState([]); // Массив фруктов
    const [errorMessage, setErrorMessage] = useState(null); // Сообщение об ошибке

    // Получение данных о поставщиках и фруктах с сервера
    useEffect(() => {
        // Запрос на получение данных о поставщиках
        axios.get('/api/suppliers')
            .then(response => setSuppliers(response.data))
            .catch(error => console.error(error));

        // Запрос на получение данных о фруктах
        axios.get('/api/fruits')
            .then(response => setFruits(response.data))
            .catch(error => console.error(error));
    }, []);

    const handleAddItem = () => {
        // Добавить новый фрукт в список items
        setItems([...items, { fruitId: '', quantity: '' }]);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Проверка, что пользователь выбрал поставщика и фрукты
        if (!supplierId || !items.every(item => item.fruitId && item.quantity > 0)) {
            setErrorMessage('Пожалуйста, выберите поставщика и фрукты.');
            return;
        }

        const deliveryDto = {
            supplierId,
            deliveryDate: deliveryDate.toLocaleDateString(), // Форматирование даты
            items,
        };

        try {
            const response = await axios.post('/api/deliveries', deliveryDto);
            // Обработать ответ от сервера
            console.log(response.data);
            setErrorMessage(null); // Сбросить сообщение об ошибке
        } catch (error) {
            // Обработать ошибку
            console.error(error);
            setErrorMessage(error.message); // Установить сообщение об ошибке
        }
    };

    const handleClearForm = () => {
        setSupplierId('');
        setDeliveryDate(new Date());
        setItems([]);
        setErrorMessage(null);
    };

    return (
        <form onSubmit={handleSubmit}>
            {errorMessage && <div className="error">{errorMessage}</div>}
            <div>
                <label htmlFor="supplierId">Поставщик:</label>
                <select id="supplierId" value={supplierId} onChange={(e) => setSupplierId(e.target.value)}>
                    <option value="">Выберите поставщика</option> {/* Добавлено пустое значение */}
                    {suppliers.map(supplier => (
                        <option key={supplier.id} value={supplier.id}>{supplier.name}</option>
                    ))}
                </select>
            </div>
            <div>
                <label htmlFor="deliveryDate">Дата поставки:</label>
                <input type="date" id="deliveryDate" value={deliveryDate.toISOString().slice(0, 10)} onChange={(e) => setDeliveryDate(new Date(e.target.value))} />
            </div>
            <div>
                <h3>Фрукты:</h3>
                <table className="table">
                    <thead>
                    <tr>
                        <th>Фрукт</th>
                        <th>Количество</th>
                        <th>Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                    {items.map((item, index) => (
                        <tr key={index}>
                            <td>
                                <select value={item.fruitId} onChange={(e) => setItems(items.map((i, idx) => idx === index ? { ...i, fruitId: e.target.value } : i))}>
                                    <option value="">Выберите фрукт</option> {/* Добавлено пустое значение */}
                                    {fruits.map(fruit => (
                                        <option key={fruit.id} value={fruit.id}>{fruit.type}</option>
                                    ))}
                                </select>
                            </td>
                            <td>
                                <input type="number" min="1" value={item.quantity} onChange={(e) => setItems(items.map((i, idx) => idx === index ? { ...i, quantity: parseInt(e.target.value) } : i))} />
                            </td>
                            <td>
                                <button type="button" onClick={() => setItems(items.filter((_, idx) => idx !== index))}>Удалить</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                <button type="button" onClick={handleAddItem}>Добавить фрукт</button>
            </div>
            <button type="submit">Создать поставку</button>
            <button type="button" onClick={handleClearForm}>Очистить</button> {/* Кнопка "Очистить" */}
        </form>
    );
}

export default DeliveryForm;
