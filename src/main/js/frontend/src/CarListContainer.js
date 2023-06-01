import React, {useEffect, useState} from 'react';
import axios from 'axios';
import CarSearchForm from "./CarSearchForm";
import CarTableForm from "./CarTableForm";

axios.defaults.baseURL = 'http://localhost:8080';

const CarListContainer = ({user}) => {
    const [searchedShowroomId, setSearchedShowroomId] = useState('');

    const handleSearch = (showroomId) => {
        setSearchedShowroomId(showroomId);
    };

    useEffect(() => {
        const fetchAllShowrooms = async () => {
            try {
                await axios.get('/showrooms');
            } catch (error) {
                console.error(error);
            }
        };

        fetchAllShowrooms();
    }, []);

    return (
        <div>
            {user && <p>Welcome, <strong>{user.email}</strong>! Your role: <strong>{user.role}</strong></p>}
            <CarSearchForm onSearch={handleSearch} user={user}/>
            {searchedShowroomId && <CarTableForm selectedShowroomId={searchedShowroomId} user={user}/>}
        </div>
    );
};

export default CarListContainer;
