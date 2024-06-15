import React from 'react';
import {render, fireEvent} from '@testing-library/react';
import RegisterPageTenant from './RegisterPageTenant';

describe('RegisterPageTenant', () => {
    it('renders without crashing', () => {
        render(<RegisterPageTenant/>);
    });

    it('has empty initial state', () => {
        const {getByLabelText} = render(<RegisterPageTenant/>);

        expect(getByLabelText(/First Name:/i).value).toBe('');
        expect(getByLabelText(/Last Name:/i).value).toBe('');
        expect(getByLabelText(/Email:/i).value).toBe('');
        expect(getByLabelText(/Apartment\/Top:/i).value).toBe('');
        expect(getByLabelText(/Property:/i).value).toBe('');
        expect(getByLabelText(/Password:/i).value).toBe('');
    });

    it('updates state on input change', () => {
        const {getByLabelText} = render(<RegisterPageTenant/>);

        fireEvent.change(getByLabelText(/First Name:/i), {target: {value: 'Test First Name'}});
        fireEvent.change(getByLabelText(/Last Name:/i), {target: {value: 'Test Last Name'}});
        fireEvent.change(getByLabelText(/Email:/i), {target: {value: 'test@example.com'}});
        fireEvent.change(getByLabelText(/Apartment\/Top:/i), {target: {value: 'Test Apartment'}});
        fireEvent.change(getByLabelText(/Property:/i), {target: {value: 'Test Property'}});
        fireEvent.change(getByLabelText(/Password:/i), {target: {value: 'password123'}});

        expect(getByLabelText(/First Name:/i).value).toBe('Test First Name');
        expect(getByLabelText(/Last Name:/i).value).toBe('Test Last Name');
        expect(getByLabelText(/Email:/i).value).toBe('test@example.com');
        expect(getByLabelText(/Apartment\/Top:/i).value).toBe('Test Apartment');
        expect(getByLabelText(/Property:/i).value).toBe('Test Property');
        expect(getByLabelText(/Password:/i).value).toBe('password123');
    });

    it('submits the form', () => {
        const {getByLabelText, getByRole} = render(<RegisterPageTenant/>);
        const consoleSpy = jest.spyOn(console, 'log');

        fireEvent.change(getByLabelText(/First Name:/i), {target: {value: 'Test First Name'}});
        fireEvent.change(getByLabelText(/Last Name:/i), {target: {value: 'Test Last Name'}});
        fireEvent.change(getByLabelText(/Email:/i), {target: {value: 'test@example.com'}});
        fireEvent.change(getByLabelText(/Apartment\/Top:/i), {target: {value: 'Test Apartment'}});
        fireEvent.change(getByLabelText(/Property:/i), {target: {value: 'Test Property'}});
        fireEvent.change(getByLabelText(/Password:/i), {target: {value: 'password123'}});

        fireEvent.click(getByRole('button', {name: /Register/i}));

        expect(consoleSpy).toHaveBeenCalledWith({
            firstName: 'Test First Name',
            lastName: 'Test Last Name',
            email: 'test@example.com',
            apartment: 'Test Apartment',
            property: 'Test Property',
            password: 'password123'
        });
    });
});