package com.kesstec;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class MockitoLearningTest{

    @Test
    public void testInterfaceMock(){
        assertNotNull(mock(Person.class));
    }

    @Test
    public void testImplMock(){
        assertNotNull(mock(PersonImpl.class));
    }

    @Test
    public void testDefaultValues(){
        Person mockPerson = mock(PersonImpl.class);

        assertNotNull(mockPerson.getChildren());
        assertEquals(0, mockPerson.getChildren().size());

        assertFalse(mockPerson.isCitizen());

        assertEquals(new Integer(0), mockPerson.getAge());

        assertNotNull(mockPerson.getSchedule());
    }

    @Test
    public void testWhen(){
        Person mockPerson = mock(PersonImpl.class);

        when(mockPerson.getAge()).thenReturn(35);

        assertEquals(new Integer(35), mockPerson.getAge());
    }

    @Test
    public void testVerify(){
        Person mockPerson = mock(PersonImpl.class);

        mockPerson.getAge();

        verify(mockPerson).getAge();
    }

    @Test
    public void testVerifyTimes(){
        Person mockPerson = mock(PersonImpl.class);

        mockPerson.getAge();
        mockPerson.getAge();

        verify(mockPerson, times(2)).getAge();
    }

    @Test
    public void testVerifyAtLeastOnce(){
        Person mockPerson = mock(PersonImpl.class);

        mockPerson.getAge();
        mockPerson.getAge();

        verify(mockPerson, atLeastOnce()).getAge();
    }

    @Test
    public void testVerifyNoMoreInteractions(){
        Person mockPerson = mock(PersonImpl.class);

        mockPerson.getAge();

        verify(mockPerson).getAge();
        verifyNoMoreInteractions(mockPerson);
    }

    @Test(expected = RuntimeException.class)
    public void testThenThrow(){
        Person mockPerson = mock(PersonImpl.class);

        when(mockPerson.getAge()).thenThrow(new RuntimeException("test exception"));

        mockPerson.getAge();
    }

    @Test(expected = RuntimeException.class)
    public void testDoThrow(){
        Person mockPerson = mock(PersonImpl.class);

        doThrow(new RuntimeException("test exception")).when(mockPerson).haveChild();

        mockPerson.haveChild();
    }

    @Test
    public void testCheckAgeOf_UnderAge(){
        Person mockPerson = mock(PersonImpl.class);

        when(mockPerson.getAge()).thenReturn(20);

        Bouncer bouncer = new Bouncer();

        assertFalse(bouncer.checkAgeOf(mockPerson));
    }

    @Test
    public void testCheckAgeOf_OfAge(){
        Person mockPerson = mock(PersonImpl.class);

        when(mockPerson.getAge()).thenReturn(21);

        Bouncer bouncer = new Bouncer();

        assertTrue(bouncer.checkAgeOf(mockPerson));
    }

    @Test(expected = NullPointerException.class)
    public void testCheckAgeOf_Null(){
        Person mockPerson = mock(PersonImpl.class);

        when(mockPerson.getAge()).thenReturn(null);

        Bouncer bouncer = new Bouncer();

        bouncer.checkAgeOf(mockPerson);
    }
}

interface Person{
    List<Person> getChildren();
    Boolean isCitizen();
    Integer getAge();
    Map<String, Date> getSchedule();
    void haveChild();
}

class Bouncer{
    public boolean checkAgeOf(Person customer){
        return customer.getAge() >= 21;
    }
}

class PersonImpl implements Person{

    public List<Person> getChildren() { return null; }

    public Boolean isCitizen() { return null; }

    public Integer getAge() { return null; }

    public Map<String, Date> getSchedule() { return null; }

    public void haveChild() { }
}
