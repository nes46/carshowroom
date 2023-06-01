import React, {useState} from 'react';
import RegistrationForm from './RegistrationForm';
import LoginForm from './LoginForm';
import CarListContainer from './CarListContainer';

const UserForm = () => {
    const [isRegistrationFormVisible, setIsRegistrationFormVisible] = useState(true);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [user, setUser] = useState(null);

    const toggleForm = () => {
        setIsRegistrationFormVisible((prevState) => !prevState);
    };

    const handleLogin = (loggedInUser) => {
        setIsLoggedIn(true);
        setUser(loggedInUser);
    };

    const handleLogout = () => {
        setIsLoggedIn(false);
        setUser(null);
    };

    return (
        <div className="container">
            <div className="my-4 d-flex justify-content-center">
                {!isLoggedIn ? (
                    <div>
                        <button className="btn btn-primary mx-2" onClick={toggleForm}>
                            {isRegistrationFormVisible ? 'Switch to Login' : 'Switch to Registration'}
                        </button>
                    </div>
                ) : (
                    <div>
                        <button className="btn btn-primary mx-2" onClick={handleLogout}>
                            Logout
                        </button>
                    </div>
                )}
            </div>
            <div className="row justify-content-center">
                <div className="col-md-11">
                    <div className="card">
                        <div className="card-body">
                            {isLoggedIn ? (
                                <CarListContainer user={user}/>
                            ) : isRegistrationFormVisible ? (
                                <RegistrationForm onLogin={handleLogin}/>
                            ) : (
                                <LoginForm onLogin={handleLogin}/>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default UserForm;
