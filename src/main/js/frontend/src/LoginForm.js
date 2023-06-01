import React, {useState} from 'react';
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8080';

const LoginForm = ({onLogin}) => {
    const [loginData, setLoginData] = useState({
        email: 'admin@mail.ru',
        password: '123456'
    });
    const [errorMessage, setErrorMessage] = useState('');

    const handleLoginChange = (e) => {
        const {name, value} = e.target;
        setLoginData((prevState) => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleLoginSubmit = (e) => {
        e.preventDefault();
        axios
            .post('/user/sign-in', loginData)
            .then((response) => {
                console.log(response.data);
                const user = response.data;
                onLogin(user);
            })
            .catch((error) => {
                console.error(error);
                if (error.response && error.response.data) {
                    setErrorMessage(error.response.data);
                } else {
                    setErrorMessage('Login failed. Please try again.');
                }
            });
    };

    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleLoginSubmit}>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">
                        Email:
                    </label>
                    <input
                        type="text"
                        name="email"
                        value={loginData.email}
                        onChange={handleLoginChange}
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
                        value={loginData.password}
                        onChange={handleLoginChange}
                        className="form-control"
                        placeholder="Enter a strong password"
                        id="password"
                    />
                </div>
                {errorMessage && (
                    <div className="alert alert-danger" role="alert">
                        {errorMessage}
                    </div>
                )}
                <button type="submit" className="btn btn-primary">
                    Sign In
                </button>
            </form>
        </div>
    );
};

export default LoginForm;
