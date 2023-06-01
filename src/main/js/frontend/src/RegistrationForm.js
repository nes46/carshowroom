import React, {useEffect, useState} from 'react';
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8080';

const RegistrationForm = ({onLogin}) => {

    const [registrationData, setRegistrationData] = useState({
        email: '',
        password: '',
        showroomId: ''
    });

    const [showrooms, setShowrooms] = useState([]);
    const [errorMessage, setErrorMessage] = useState('');

    const handleRegistrationChange = (e) => {
        const {name, value} = e.target;
        setRegistrationData((prevState) => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleRegistrationSubmit = (e) => {
        e.preventDefault();
        axios
            .post('/user/sign-up', registrationData)
            .then((response) => {
                console.log(response.data);
                onLogin(response.data);
            })
            .catch((error) => {
                console.error(error);
                if (error.response && error.response.data) {
                    setErrorMessage(error.response.data);
                } else {
                    setErrorMessage('Registration failed. Please try again.');
                }
            });
    };

    useEffect(() => {
        const fetchShowrooms = async () => {
            try {
                const response = await axios.get('/showrooms');
                setShowrooms(response.data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchShowrooms();
    }, []);

    return (
        <div>
            <h3>Registration</h3>
            <form onSubmit={handleRegistrationSubmit}>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">
                        Email:
                    </label>
                    <input
                        type="text"
                        name="email"
                        value={registrationData.email}
                        onChange={handleRegistrationChange}
                        className="form-control"
                        placeholder="example@example.com"
                        id="email"
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="password" className="form-label">
                        Password:
                    </label>
                    <input
                        type="password"
                        name="password"
                        value={registrationData.password}
                        onChange={handleRegistrationChange}
                        className="form-control"
                        placeholder="Enter a strong password"
                        id="password"
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="showroomId" className="form-label">
                        Showroom:
                    </label>
                    <select
                        name="showroomId"
                        value={registrationData.showroomId}
                        onChange={handleRegistrationChange}
                        className="form-control"
                        id="showroomId"
                    >
                        <option value="">Select a showroom...</option>
                        {showrooms.map((showroom) => (
                            <option key={showroom.id} value={showroom.id}>
                                {showroom.name}
                            </option>
                        ))}
                    </select>
                </div>
                {errorMessage && (
                    <div className="alert alert-danger" role="alert">
                        {errorMessage}
                    </div>
                )}
                <button type="submit" className="btn btn-primary">
                    Sign Up
                </button>
            </form>
        </div>
    );

};

export default RegistrationForm;