import React, {useEffect, useState} from 'react';
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8080';

const CarSearchForm = ({onSearch, user}) => {
    const [allShowrooms, setAllShowrooms] = useState([]);
    const [selectedShowroomId, setSelectedShowroomId] = useState('');

    useEffect(() => {
        const fetchAllShowrooms = async () => {
            try {
                const response = await axios.get('/showrooms');
                setAllShowrooms(response.data);

                if (user.role === 'CUSTOMER') {
                    setSelectedShowroomId(user.showroomId);
                    onSearch(user.showroomId);
                }
            } catch (error) {
                console.error(error);
            }
        };

        fetchAllShowrooms();
    }, [user.role, user.showroomId, onSearch]);


    const handleShowroomIdChange = (e) => {
        if (user.role === 'ADMIN') {
            setSelectedShowroomId(e.target.value);
        }
    };

    const handleSearch = (e) => {
        e.preventDefault();
        onSearch(selectedShowroomId);
    };

    return (
        <div>
            <form onSubmit={handleSearch}>
                <div className="mb-2">
                    <select
                        name="showroomId"
                        value={selectedShowroomId}
                        onChange={handleShowroomIdChange}
                        className="form-control"
                        id="showroomId"
                        disabled={user.role !== 'ADMIN'}
                    >
                        <option value="">Select a showroom...</option>
                        {allShowrooms.map((showroom) => (
                            <option key={showroom.id} value={showroom.id}>
                                {showroom.name}
                            </option>
                        ))}
                    </select>
                </div>
                {user.role === 'ADMIN' && (
                    <button type="submit" className="btn btn-primary d-flex justify-content-start mb-3">
                        Search
                    </button>
                )}
            </form>
        </div>
    );
};

export default CarSearchForm;
