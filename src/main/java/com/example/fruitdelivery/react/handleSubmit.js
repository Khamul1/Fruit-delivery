const handleSubmit = async (event) => {
    event.preventDefault();

    const deliveryDto = {
        supplierId,
        deliveryDate,
        items,
    };

    try {
        const response = await axios.post('/api/deliveries', deliveryDto);
        // Обработать ответ от сервера
        console.log(response.data);
    } catch (error) {
        // Обработать ошибку
        console.error(error);
    }
};
