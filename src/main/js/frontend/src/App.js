import React from 'react';
import UserForm from './UserForm';
import './App.css';

const App = () => {
    return (
        <div className="d-flex flex-column min-vh-100">
            <header className="bg-light py-3">
                <div className="container text-center">
                    <h1 className="mb-0">Car Showroom</h1>
                    <h2 className="text-muted">Find Your Dream Car</h2>
                </div>
            </header>
            <main className="flex-grow-1">
                <div className="container text-center">
                    <UserForm/>
                </div>
            </main>
            <footer className="bg-light py-3 text-center">
                <div className="container">
                    <p className="text-muted">by Komarnitskaia Daria</p>
                </div>
            </footer>
        </div>
    );
};

export default App;