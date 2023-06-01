import React, {useEffect, useState} from "react";
import axios from "axios";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSort} from "@fortawesome/free-solid-svg-icons";

const CarTableForm = ({selectedShowroomId, user}) => {
    const [cars, setCars] = useState([]);
    const [searchQuery, setSearchQuery] = useState('');
    const [sortOrder, setSortOrder] = useState("asc");
    const [validationErrors, setValidationErrors] = useState({
        model: '',
        brand: '',
        year: '',
        price: '',
    });
    const [newCar, setNewCar] = useState({
        model: "",
        brand: "",
        year: "",
        price: "",
        showroomId: selectedShowroomId,
    });

    useEffect(() => {
        const fetchCars = async () => {
            try {
                const response = await axios.get(`/cars/showroom/${selectedShowroomId}`);
                setCars(response.data);
            } catch (error) {
                console.error(error);
            }
        };

        if (selectedShowroomId) {
            fetchCars();
        }
    }, [selectedShowroomId]);

    const handleSearch = (event) => {
        setSearchQuery(event.target.value);
    };

    const handleSort = () => {
        const sortedCars = [...cars].sort((a, b) => {
            if (sortOrder === "asc") {
                return a.brand.localeCompare(b.brand);
            } else {
                return b.brand.localeCompare(a.brand);
            }
        });

        setCars(sortedCars);
        setSortOrder(sortOrder === "asc" ? "desc" : "asc");
    };

    const handleNewCarInputChange = (e) => {
        const {name, value} = e.target;
        let newValue = name === "price" ? parseFloat(value) : value;

        if (name === "price" && newValue < 1) {
            newValue = 1;
        } else if (name === "year") {
            const currentYear = new Date().getFullYear();
            newValue =
                parseFloat(value) >= currentYear - 20 && parseFloat(value) <= currentYear
                    ? parseFloat(value)
                    : currentYear - 20;
        } else {
            newValue = value;
        }

        setNewCar((prevCar) => ({...prevCar, [name]: newValue}));
        setValidationErrors((prevErrors) => ({...prevErrors, [name]: ''}));
    };

    const handleInputChange = (e, index) => {
        const {name, value} = e.target;
        const newValue = name === "price" ? parseFloat(value) : value;

        setCars((prevCars) => {
            const updatedCars = [...prevCars];
            updatedCars[index] = {...updatedCars[index], [name]: newValue};
            return updatedCars;
        });
    };


    const handleSave = async (index) => {
        try {
            const editedCar = cars[index];
            const response = await axios.put(`/cars/${editedCar.id}`, editedCar);
            const updatedCar = response.data;
            setCars((prevCars) =>
                prevCars.map((car, i) => (i === index ? updatedCar : car))
            );
        } catch (error) {
            console.error(error);
        }
    };

    const handleAddCar = async () => {
        try {
            let errorsExist = false;
            const newErrors = {};

            if (newCar.model.trim() === "") {
                errorsExist = true;
                newErrors.model = "Model is required.";
            }

            if (newCar.brand.trim() === "") {
                errorsExist = true;
                newErrors.brand = "Brand is required.";
            }

            if (newCar.year < 2000 || newCar.year > new Date().getFullYear()) {
                errorsExist = true;
                newErrors.year = "Invalid year.";
            }

            if (newCar.price < 1) {
                errorsExist = true;
                newErrors.price = "Price must be greater than 0.";
            }

            if (errorsExist) {
                setValidationErrors(newErrors);
                return;
            }


            const response = await axios.post("/cars", newCar);
            const addedCar = {
                ...response.data,
                price: parseFloat(response.data.price),
            };
            setCars((prevCars) => [...prevCars, addedCar]);
            setNewCar({
                model: "",
                brand: "",
                year: "",
                price: "",
                showroomId: selectedShowroomId,
            });
        } catch (error) {
            console.error(error);
        }
    };

    const handleDelete = async (id) => {
        try {
            const confirmed = window.confirm("Are you sure you want to delete this car?");
            if (confirmed) {
                await axios.delete(`/cars/${id}`);
                setCars((prevCars) => prevCars.filter((car) => car.id !== id));
            }
        } catch (error) {
            console.error(error);
        }
    };

    const brandOptions = ["AUDI", "BMW", "MERCEDES"];

    const modelOptions = {
        AUDI: ["Q7", "A4", "TT"],
        BMW: ["SERIES_5", "SERIES_7", "X5"],
        MERCEDES: ["S_KLASS", "E_KLASS", "C_KLASS"],
    };

    const yearOptions = [];
    const currentYear = new Date().getFullYear();
    for (let year = currentYear; year >= currentYear - 20; year--) {
        yearOptions.push(year);
    }

    return (
        <div>
            <input
                type="text"
                value={searchQuery}
                onChange={handleSearch}
                placeholder="Search by Model"
                className="form-control"
            />
            <table className="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>
                        <button className="sort-button" onClick={handleSort}>
                            Brand{" "}
                            {sortOrder === "asc" ? (
                                <FontAwesomeIcon icon={faSort} className="sort-icon"/>
                            ) : (
                                <FontAwesomeIcon icon={faSort} className="sort-icon rotate"/>
                            )}
                        </button>
                    </th>
                    <th>Model</th>
                    <th>Year</th>
                    <th>Price $</th>
                    <th>Available</th>
                    {user.role === 'ADMIN' && <th>Actions</th>}
                </tr>
                </thead>
                <tbody>
                {cars
                    .filter((car) =>
                        car.model.toLowerCase().includes(searchQuery.toLowerCase())
                    )
                    .map((car, index) => (
                        <tr key={car.id}>
                            <td>{car.id}</td>
                            <td>{car.brand}</td>
                            <td>{car.model}</td>
                            <td>{car.year}</td>
                            {user.role === 'ADMIN' ? (
                                <>
                                    <td>
                                        <input
                                            type="number"
                                            name="price"
                                            value={car.price}
                                            onChange={(e) => handleInputChange(e, index)}
                                            className="form-control"
                                        />
                                    </td>
                                    <td>
                                        <select
                                            name="available"
                                            value={car.available}
                                            onChange={(e) => handleInputChange(e, index)}
                                            className="form-control"
                                        >
                                            <option value={true}>Yes</option>
                                            <option value={false}>No</option>
                                        </select>
                                    </td>
                                    <td className="action-buttons d-flex">
                                        <div className="btn-group">
                                            <button
                                                type="button"
                                                className="btn btn-primary me-2"
                                                onClick={() => handleSave(index)}
                                            >
                                                Save
                                            </button>
                                            <button
                                                type="button"
                                                className="btn btn-danger"
                                                onClick={() => handleDelete(car.id)}
                                            >
                                                Delete
                                            </button>
                                        </div>
                                    </td>
                                </>
                            ) : (
                                <>
                                    <td>{car.price}</td>
                                    <td>{car.available ? <span>&#10004;</span> : <span>&#10006;</span>}</td>
                                </>
                            )}
                        </tr>
                    ))}
                {user.role === 'ADMIN' && (
                    <tr>
                        <td></td>
                        <td>
                            <select
                                name="brand"
                                value={newCar.brand}
                                onChange={handleNewCarInputChange}
                                className={"form-control " + (validationErrors.brand ? "is-invalid" : "")}
                            >
                                <option value="">Brand</option>
                                {brandOptions.map((brand) => (
                                    <option key={brand} value={brand}>
                                        {brand}
                                    </option>
                                ))}
                            </select>
                            {validationErrors.brand && (
                                <div className="invalid-feedback">{validationErrors.brand}</div>
                            )}
                        </td>
                        <td>
                            <select
                                name="model"
                                value={newCar.model}
                                onChange={handleNewCarInputChange}
                                className={"form-control " + (validationErrors.model ? "is-invalid" : "")}
                            >
                                <option value="">Model</option>
                                {newCar.brand &&
                                    modelOptions[newCar.brand].map((model) => (
                                        <option key={model} value={model}>
                                            {model}
                                        </option>
                                    ))}
                            </select>
                            {validationErrors.model && (
                                <div className="invalid-feedback">{validationErrors.model}</div>
                            )}
                        </td>
                        <td>
                            <select
                                name="year"
                                value={newCar.year}
                                onChange={handleNewCarInputChange}
                                className={"form-control " + (validationErrors.year ? "is-invalid" : "")}
                            >
                                <option value="">Year</option>
                                {yearOptions.map((year) => (
                                    <option key={year} value={year}>
                                        {year}
                                    </option>
                                ))}
                            </select>
                            {validationErrors.year && (
                                <div className="invalid-feedback">{validationErrors.model}</div>
                            )}
                        </td>
                        <td>
                            <input
                                type="number"
                                name="price"
                                value={newCar.price}
                                onChange={handleNewCarInputChange}
                                className={"form-control " + (validationErrors.price ? "is-invalid" : "")}
                                placeholder="Price"
                                min={1}
                            />
                            {validationErrors.price && (
                                <div className="invalid-feedback">{validationErrors.price}</div>
                            )}
                        </td>
                        <td></td>
                        <td className="action-buttons">
                            <button type="button" className="btn btn-success" onClick={handleAddCar}>
                                Add
                            </button>
                        </td>
                    </tr>
                )}
                </tbody>
            </table>
        </div>
    );
};

export default CarTableForm;